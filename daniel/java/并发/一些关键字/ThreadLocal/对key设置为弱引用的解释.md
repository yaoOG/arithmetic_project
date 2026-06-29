
### 问题描述
如果 key 是强引用，只要线程还在（比如线程池里的线程一直存活），ThreadLocalMap 就会一直引用 ThreadLocal 对象，导致这个 ThreadLocal 永远无法被 GC，即使你在业务代码里已经没任何引用了。

使用 WeakReference 后，当 ThreadLocal 对象没有外部强引用时，GC 会回收它，Map 中的 key 变为 null


面试官您好！您刚才说的这段话，直接切中了 `ThreadLocal` 内存泄漏的核心物理本质。很多开发者只知道背诵“不用了要调 `remove()`”，但如果不从 **“JVM 堆内存引用链”** 的微观视角去看，是很难真正理解为什么强引用会引发灾难、而弱引用又是如何自救的。

作为一个老兵，我带您画两幅内存引用图，用最直观的“连线拆解”，帮您把这个机制彻底吃透。

首先，我们要对齐一个大前提：**`ThreadLocal` 的底层，数据并不是存在 ThreadLocal 实例里的，而是存在当前 `Thread` 线程对象持有的 `ThreadLocalMap` 里。** 这个 Map 的底层是一个数组，数组里装的是一个一个的 `Entry` 节点。

---

### 一、 假设：如果 Entry 的 Key 是“强引用”

我们先来看第一种情况：如果当年 Java 官方把 `Entry` 的 Key 设计成了普通的强引用。

#### 1. 物理引用链条：

此时，内存中存在两条引用链：

* **链条 A（业务生命周期）**：你的业务代码（比如某个 Service 里的局部变量） $\rightarrow$ 强引用 $\rightarrow$ `ThreadLocal` 对象。
* **链条 B（线程生命周期）**：当前 `Thread` 对象 $\rightarrow$ 强引用 $\rightarrow$ `ThreadLocalMap` $\rightarrow$ `Entry[]` 数组 $\rightarrow$ `Entry` 对象 $\rightarrow$ **强引用（假设）** $\rightarrow$ `ThreadLocal` 对象。

#### 2. 灾难发生的过程：

1. 当你的业务请求执行结束，方法弹栈，**“链条 A”断开**。此时，你的业务代码再也摸不到这个 `ThreadLocal` 对象了。
2. **GC 开始工作**。由于这是**线程池**里的线程（核心线程永远不会死），**“链条 B”依然完好无损**。
3. 从 GC Roots 出发，顺着“链条 B”一路向下，依然能够找到 `ThreadLocal` 对象。
4. **结果**：因为还存在强引用，**`ThreadLocal` 对象绝对不会被回收**。它会像幽灵一样永远卡在堆内存里，只要线程不死，这块内存就永远释放不掉。随着请求越来越多，内存逐渐被蚕食，最终引发 **`OutOfMemoryError`（OOM）**。

---

### 二、 现实：Entry 的 Key 被设计为“弱引用”（`WeakReference`）

Java 官方为了挽救这个局面，让 `Entry` 继承了 `WeakReference<ThreadLocal<?>>`。

#### 1. 物理引用链条：

* **链条 A（业务生命周期）**：业务代码 $\rightarrow$ 强引用 $\rightarrow$ `ThreadLocal` 对象。
* **链条 B（线程生命周期）**：`Thread` $\rightarrow$ 强引用 $\rightarrow$ `ThreadLocalMap` $\rightarrow$ `Entry[]` $\rightarrow$ `Entry` $\rightarrow$ **弱引用（WeakReference）** $\rightarrow$ `ThreadLocal` 对象。

#### 2. 弱引用的自救过程：

1. 当业务方法执行结束，**“链条 A”断开**。此时，整个 JVM 堆内存中，只有“链条 B”上的那一根“弱引用”还牵着 `ThreadLocal` 对象。
2. **弱引用的铁律**：JVM 在进行垃圾回收时，**不管当前内存够不够，只要一个对象只剩下弱引用牵着，它就必须死！**
3. **结果**：GC 挥舞铁拳，`ThreadLocal` 对象被**顺利回收**。
4. 此时，神奇的事情发生了：由于 `ThreadLocal` 没了，`Entry` 里的 Key 变成了 **`null`**。

---

### 三、 ⚠️ 致命的下半场：为什么弱引用了依然会发生内存泄漏？

面试官听到这，一定会冷不丁追问一句：**“既然 Key 变成 `null` 了，那不就安全了吗？为什么大家还说 ThreadLocal 会内存泄漏？”**

这就是最精妙的地方。请注意看此时的堆内存遗留状态：
虽然 Key 变成了 `null`，但是 **`Entry` 里的 `Value` 还在啊！**（比如你存进去的那个 10MB 的大 User 对象）。

#### 此时的残留引用链：

`Thread` 对象 $\rightarrow$ 强引用 $\rightarrow$ `ThreadLocalMap` $\rightarrow$ `Entry[]` $\rightarrow$ `Entry` $\rightarrow$ **强引用** $\rightarrow$ `Value` 对象（大对象）。

由于 Key 已经变成 `null` 了，你在 Java 代码里调用 `threadLocal.get()` 已经没有任何办法可以拿到这个 Value 了。但这块 10MB 的 Value 却依然顺着线程的强引用链条，**死死地卡在内存里无法被回收**。这，才是 `ThreadLocal` 真正的内存泄漏！

### 四、 Spring/Java 框架的“自我擦屁股”与程序员的终极纪律

Java 官方当然想到了这个问题，所以他们在 `ThreadLocalMap` 的 `get()`、`set()`、`rehash()` 方法里，写了一些**主动清理机制**：
当线程池里的下一个请求过来，再次调用同一个 ThreadLocal 的 `get()` 或 `set()` 时，MyBatis 或 Spring 底层代码会顺便遍历一下 Map，**一旦发现某个 Entry 的 Key 是 `null`，就会顺手把它的 Value 也置为 `null**`，把这根强引用链也斩断，让 GC 顺便把 Value 也回收掉。

**但这种自我清理是被动的、滞后的（必须有下一次调用才会触发）。**

* 如果这个线程由于业务打样，后面再也没有请求过来了；
* 或者这个线程接下来被去干别的事，再也没有调用过这个 ThreadLocal；

那么这个 `Value` 就会永久残留。

#### 👑 老兵的终极实战红线：

```java
public void doBusiness() {
    try {
        threadLocal.set(userInfo);
        // 执行核心清结算/业务逻辑...
    } finally {
        // 🛑 必须在 finally 块中，像关数据库连接一样，雷打不动地执行 remove()！
        threadLocal.remove(); 
    }
}

```

`remove()` 的底层源码非常干脆：直接把当前线程对应的整个 `Entry` 对象彻底从数组里挖掉、全置为 `null`。

**总结一句话回应面试官：**

> “Key 的弱引用，仅仅是保证了 `ThreadLocal` 自身这个**元数据对象**能够被及时回收、并让 Key 暴露为 `null`；但要彻底解决它引发的 **`Value` 内存泄漏**，必须依靠程序员在 `finally` 块中雷打不动的一句 **`threadLocal.remove()`** 来物理切断最后的强引用链。”