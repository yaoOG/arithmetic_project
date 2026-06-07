
---
<font color="red">✔</font> 已掌握

## 1. 核心设计思想
ThreadLocal 并不是一个存储对象的容器，它只是一个**工具类**，真正存储数据的地方是 **每个线程自己的内部 `ThreadLocalMap`**。示意图：

```
Thread-1          Thread-2
   |                 |
   v                 v
ThreadLocalMap   ThreadLocalMap
(Entry数组)      (Entry数组)
   |                 |
   v                 v
key: ThreadLocal  key: ThreadLocal (同一个对象)
value: 对象1      value: 对象2
```

同一个 ThreadLocal 实例在不同线程里可以对应不同的值，所以实现了线程隔离。

---

## 2. 数据结构：Thread 与 ThreadLocalMap
- 每个 `Thread` 对象里有一个 `threadLocals` 字段（类型 `ThreadLocal.ThreadLocalMap`），默认是 null，懒初始化。
- `ThreadLocalMap` 是一个**自定义的哈希表**，只用在 ThreadLocal 内部。它使用**数组 + 线性探测**来解决冲突。
- 它的 `Entry` 继承自 `WeakReference<ThreadLocal<?>>`，**key 是 ThreadLocal 的弱引用，value 是强引用**。

`Entry` 结构示意：
```java
static class Entry extends WeakReference<ThreadLocal<?>> {
    Object value;
    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}
```

---

## 3. set / get / remove 源码级流程

### 3.1 set 方法
```java
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);        // 返回 t.threadLocals
    if (map != null)
        map.set(this, value);              // key 是当前 ThreadLocal 实例
    else
        createMap(t, value);               // 创建 Map 并设值
}
```
- 如果线程的 `threadLocals` 为空，就 new 一个 `ThreadLocalMap`，把当前 `ThreadLocal` 作为 key 放入，然后赋值给线程。
- 如果已存在，直接以 `this` 为键存入。

### 3.2 get 方法
```java
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null)
            return (T)e.value;
    }
    return setInitialValue();   // 初始化并返回 initialValue()
}
```
- 从当前线程的 Map 里用 `this` 查找 Entry。
- 若未找到，调用 `setInitialValue()` 创建初始值（默认 null，可重写 `initialValue()`）。

### 3.3 remove 方法
```java
public void remove() {
    ThreadLocalMap m = getMap(Thread.currentThread());
    if (m != null)
        m.remove(this);
}
```
- 直接从当前线程的 Map 中移除以 `this` 为 key 的 Entry，防止内存泄漏。

---

## 4. 哈希冲突解决：线性探测
- 通过 `threadLocalHashCode`（由 `nextHashCode()` 原子生成，步长为 `0x61c88647`，能较好地散列）计算数组下标。
- 如果发生冲突，就向后依次查找（`index + 1`），直到找到空位或 key 相同的 Entry。
- 由于没有链表，不会产生链表红黑树转换的问题，但需要警惕聚集效应，因此清理机制很重要。

---

## 5. 弱引用与内存泄漏 —— 为什么这么设计

**为什么 key 要使用弱引用？**
- 如果 key 是强引用，只要线程还在（比如线程池里的线程一直存活），`ThreadLocalMap` 就会一直引用 `ThreadLocal` 对象，导致这个 `ThreadLocal` 永远无法被 GC，即使你在业务代码里已经没任何引用了。
- 使用 `WeakReference` 后，当 `ThreadLocal` 对象没有外部强引用时，GC 会回收它，Map 中的 key 变为 `null`。

**那 value 不就泄漏了吗？**
是的，Entry 的 value 是强引用，如果 key 被回收了，value 依然被 Entry 引用着，不会被回收，就产生了内存泄漏的隐患。因此 `ThreadLocalMap` 在 **get / set / remove** 时会进行清理：
- **探测式清理**：线性探测过程中发现 key 为 null 的过期 Entry，会将其 value 置为 null，并清理该 Entry。
- **启发式清理**：在 `set` 时如果找不到位置，还会批量清理一些过期 Entry。

但这个清理**不是实时的**，如果长时间不调用这些方法，value 依然会残留在内存中。
因此最佳实践是：
- 使用 **`static`** 修饰 ThreadLocal，保证 ThreadLocal 本身不会因弱引用被回收；
- **用完必须调用 `remove()`**，尤其是在线程池环境下。

---

## 6. 扩展：InheritableThreadLocal
- `InheritableThreadLocal` 可以让子线程继承父线程的值。
- 实现方式是 `Thread` 类还有一个 `inheritableThreadLocals` 字段，在创建子线程时，`Thread.init()` 会把父线程的 `inheritableThreadLocals` 拷贝一份给子线程（浅拷贝）。
- 需要注意，拷贝的是引用，如果值是可变对象，父子线程可能互相影响。

---

**总结一句话：**
ThreadLocal 通过在每个线程里维护一个以“ThreadLocal 弱引用为 key”的 Map，实现了线程级别的数据隔离；弱引用设计是为了让 ThreadLocal 对象能及时回收，但要手动 remove 才能真正避免 value 的内存泄漏。