脑图很详细，直接看脑图
![[concurrentHashMap.xmind]]


---

# ConcurrentHashMap 深度解析：从 JDK 1.7 到 1.8 的演进
![[Pasted Graphic.png]]

## 一、 JDK 1.7：分段锁 (Segmented Locking)

JDK 1.7 的核心设计思想是**“锁分离”**。其出发点是：只要多个线程操作的 Key 不在同一个“段”里，就可以并行执行，从而减少锁竞争。

### 1. 逻辑结构
![[Pasted image 20260330145452.png]]

- **层级关系**：整体是一个 `Segment` 数组，每个 `Segment` 内部维护一个类似 `HashMap` 的结构（`HashEntry` 数组 + 链表）。
    
- **锁实现**：`Segment` 类继承自 `ReentrantLock`。
    

### 2. 加锁机制

- **定位段**：执行 `put` 时，先通过 Hash 算法定位到具体的 `Segment`。
    
- **局部加锁**：仅对该 `Segment` 执行 `lock()`。
    
- **并发度**：默认并发度为 16，理论上支持 16 个线程同时写入。
    

### 3. 缺点

- **并发上限**：一旦初始化，Segment 数组大小不可变，限制了最高并行能力。
    
- **内存开销**：每个 `Segment` 都是一个独立的大对象，内存空间利用率较低。
    

---

## 二、 JDK 1.8：CAS + Synchronized (Node 粒度锁)

JDK 1.8 彻底抛弃了 Segment，回归了类似 `HashMap` 的底层结构：**数组 + 链表 + 红黑树**。它通过 **CAS (无锁)** 和 **细粒度 synchronized (头节点锁)** 实现了更高的并发性能。


![[Pasted image 20260330145515.png]]

- 默认使用链表解决哈希冲突。  
- **树化 (Treeify)：** 当链表长度 > 8 且数组长度 >= 64 时，链表会转换为 **红黑树 (Red-Black Tree)**。这为了防止哈希攻击或冲突严重时的性能退化（从 O(n) 提升到 O(log n)）。

### 1. 写操作 (put) 的并发控制

针对不同的桶（Bucket）状态，采取了阶梯式的并发策略：

- **情况 A：数组初始化 (Lazy Loading)**
    
    - 使用 **CAS** ( `initTable` 方法) 抢占初始化权。只有一个线程能成功修改 `sizeCtl` 标志位，其余线程调用 `Thread.yield()` 让出 CPU。
        
- **情况 B：目标槽位为空 (No Collision)**
    
    - **机制**：直接使用 `Unsafe.compareAndSwapObject` (CAS) 尝试将新节点写入。
        
    - **亮点**：**完全无锁**。若失败（说明有其他线程抢先写入），则自旋进入下一种情况。
        
- **情况 C：目标槽位不为空 (Collision)**
    
    - **机制**：使用 `synchronized` 锁住该桶的**头节点 (Head Node)**。
        
    - **亮点**：锁粒度由“段”缩小到了“桶”。只有落在同一个 Hash 桶里的线程才会竞争，极大提升了吞吐量。
        

### 2. 读操作 (get) 的线程安全

`ConcurrentHashMap` 的 `get` 操作**全程不加锁**，性能极高。

- **原理**：核心依赖 `volatile` 关键字。
    
- **可见性保证**：Node 结构中的 `val` 和 `next` 指针均被 `volatile` 修饰。根据 **JMM（Java 内存模型）**，任何线程对节点值的修改或链表结构的变更，其他线程都能立即可见。
```java
static class Node<K,V> implements Map.Entry<K,V> {
     final int hash;
     final K key;
     volatile V val;         // 保证值的可见性
     volatile Node<K,V> next;  // 保证链表引用的可见性
     // ...
```

### 3. 协同扩容 (Multi-Threaded Transfer)

这是 JDK 1.8 最精妙的设计，允许**多线程并发迁移数据**：

- **ForwardingNode (转发节点)**：扩容时，迁移完的槽位会放置一个 Hash 值为 `MOVED (-1)` 的特殊节点。
    
- **协助机制**：当其他线程在执行 `put` 时发现目标槽位是 `ForwardingNode`，它不会阻塞，而是调用 `helpTransfer` 方法**主动认领**一部分槽位进行数据迁移，变“独占扩容”为“全民扩容”。
    

---

## 三、 核心追问：为什么用 synchronized 替换 ReentrantLock？

早年都说 `synchronized` 是重量级锁，性能差，为什么 Java 8 的 CHM 反而把它请回来了？

- **锁粒度变了**：Java 8 锁的是单个槽位的头节点。如果用 `ReentrantLock`，意味着每个节点都需要继承或包含一个 AQS 对象，这会带来极其恐怖的**内存占用**。而 `synchronized` 利用的是对象头（Mark Word）里自带的 Monitor，没有任何额外的内存开销。
    
- **JVM 的底层优化**：从 Java 6 开始，JVM 对 `synchronized` 做了极其激进的优化，引入了偏向锁、轻量级锁（自旋锁）、锁消除等机制。在只有少量竞争的细粒度锁场景下，`synchronized` 的性能早已不输甚至优于 `ReentrantLock`。

---
