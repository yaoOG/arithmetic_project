<font color="red">✔</font> 已掌握

这个问题的核心在于理解 **ThreadLocalMap 中 Entry 的 key 是弱引用**，以及 **static 变量在 Java 中的 GC 可达性**。

---

### 1. 回顾：弱引用 key 带来的隐患

`ThreadLocalMap` 的内部 Entry 是这样定义的（简化）：
```java
static class Entry extends WeakReference<ThreadLocal<?>> {
    Object value;
    Entry(ThreadLocal<?> k, Object v) {
        super(k);   // key 是弱引用
        value = v;  // value 是强引用
    }
}
```
- `key` 是 **弱引用** 指向 ThreadLocal 实例。
- `value` 是 **强引用** 指向业务数据。

**如果 ThreadLocal 实例在外部没有强引用了**（例如它被定义为局部变量或非 static 成员且对象已释放），那么：
- 下一次 GC 时，这个 ThreadLocal 实例就会被回收。
- 此时 Map 中的 Entry 的 key 变成了 `null`（弱引用被清空）。
- 但 **value 仍然被 Entry 强引用**，且因为 key 是 null，再也无法通过这个 ThreadLocal 访问到该 value，这个 Entry 就无法被正常 remove 掉。
- 只要当前线程还活着（比如线程池中的线程），这个 value 就会一直占着内存，造成**内存泄漏**。

---

### 2. static 如何“保护” ThreadLocal 不被回收

`static` 修饰的变量属于**类**，而不是实例。  
从 GC Roots（垃圾回收的根）的角度看：
- **类静态属性** 是一类 GC Root，只要类还被加载着（通常伴随整个应用生命周期），静态变量引用的对象就**永远不会被垃圾回收**。
- 因此，`private static final ThreadLocal<T> HEAD_OFFICE_ID = new ThreadLocal<>();` 这个 ThreadLocal 实例始终存在一条从 GC Root 出发的强引用链：  
  `Class对象 -> 静态字段 HEAD_OFFICE_ID -> ThreadLocal 实例`

**结果**：这个 ThreadLocal 实例永远有外部强引用，**永远不会被 GC 回收**。  
那么 Entry 的弱引用 key 也就永远不会被清空成 null，这个 key 始终有效，你可以随时通过它去 `get/set/remove`。

---

### 3. 这为什么是一种“保证”？

如果不用 static，比如写成这样：
```java
public void process() {
    ThreadLocal<String> local = new ThreadLocal<>();  // 局部变量，方法结束即无强引用
    local.set("data");
    // 忘记 remove...
}
```
- 方法结束后，`local` 这个局部变量出栈，ThreadLocal 实例就没有强引用了。
- 下一次 GC，弱引用 key 被清理，Map 中出现 key 为 null 的 Entry，value 泄漏。
- 你想再通过这个 ThreadLocal 来清理 value？**做不到了**，因为连这个 ThreadLocal 对象都没了。

使用 static 后，ThreadLocal 对象与类同寿命，你**永远可以通过它找到对应线程里存的 value**，并调用 `remove()` 进行清理。所以它：
- 避免了 key 被回收导致的“失联”问题。
- 让你在程序任何地方（如 Filter 的 finally 块）都能拿到同一个 ThreadLocal 实例来执行清理。

---

### 4. 但这并不等于“不用 remove”

static 只是**延长了 ThreadLocal 的声明周期，保证 key 不丢失**，但 value 依然是强引用。  
如果线程长期存活（如线程池），而且你一直 set 不 remove：
- key 还在，但 value 会越堆越多（每次 set 新值，旧的 value 可能被你丢弃，但 Entry 还在数组里，直到发生冲突清理或被覆盖）。
- 即便 key 有效，你也需要在适当时机主动 `remove()`，才能真正释放 value 占用的内存。

所以最佳实践是：
```java
private static final ThreadLocal<Long> STORE_ID = new ThreadLocal<>();

try {
    STORE_ID.set(123L);
    // 执行业务逻辑
} finally {
    STORE_ID.remove();  // 必须！
}
```

---

**总结一句话**：  
static 保证了 ThreadLocal 实例本身一直存在强引用，不会被 GC 回收，因此它作为弱引用 key 永远不会变成 null，你能始终通过它安全地访问、清理线程局部变量；但 value 的内存泄漏风险依然需要你主动调用 `remove()` 来规避。