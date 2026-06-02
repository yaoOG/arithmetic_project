在 Java 中，`Deque`（全称 **Double Ended Queue**，双端队列）是一个极其强大的接口，继承自 `Queue`。它允许在队列的两端进行插入、删除和检查操作。

你可以将 `Deque` 既当作 **先进先出 (FIFO)** 的队列使用，也可以当作 **后进先出 (LIFO)** 的栈（Stack）使用。

---

## 1. 核心 API 方法概览

`Deque` 的方法设计非常有规律，通常分为两类：

1. **抛出异常**：如果操作失败（如在空队列中取元素），直接抛出异常。
    
2. **返回特殊值**：如果操作失败，返回 `null` 或 `false`。
    

### 头部操作 (First)

|**操作类型**|**抛出异常**|**返回特殊值 (推荐)**|
|---|---|---|
|**插入 (添加)**|`addFirst(e)`|`offerFirst(e)`|
|**删除 (移除)**|`removeFirst()`|`pollFirst()`|
|**检查 (查看)**|`getFirst()`|`peekFirst()`|

### 尾部操作 (Last)

|**操作类型**|**抛出异常**|**返回特殊值 (推荐)**|
|---|---|---|
|**插入 (添加)**|`addLast(e)`|`offerLast(e)`|
|**删除 (移除)**|`removeLast()`|`pollLast()`|
|**检查 (查看)**|`getLast()`|`peekLast()`|

---

## 2. 作为“栈”或“队列”的使用习惯

虽然你可以随意调用上述方法，但在 Java 中，为了代码可读性，通常会使用特定的方法别名：

### 像栈 (Stack) 一样使用 (LIFO)

Java 官方推荐使用 `Deque` 替代遗留的 `Stack` 类。

- **压栈**：`push(e)`（等同于 `addFirst(e)`）
    
- **出栈**：`pop()`（等同于 `removeFirst()`）
    
- **查看栈顶**：`peek()`（等同于 `peekFirst()`）
    

### 像队列 (Queue) 一样使用 (FIFO)

- **入队**：`add(e)`（等同于 `addLast(e)`）或 `offer(e)`
    
- **出队**：`remove()`（等同于 `removeFirst()`）或 `poll()`
    
- **查看队头**：`element()` 或 `peek()`
    

---

## 3. 其他常用方法

除了首尾操作，`Deque` 还提供了一些实用的辅助方法：

- **`removeFirstOccurrence(Object o)`**：从头部向尾部遍历，删除第一个匹配的元素。
    
- **`removeLastOccurrence(Object o)`**：从尾部向头部遍历，删除第一个匹配的元素。
    
- **`size()`**：返回队列中的元素个数。
    
- **`contains(Object o)`**：检查是否包含某个元素。
    
- **`iterator()`**：返回从头到尾的迭代器。
    
- **`descendingIterator()`**：返回**从尾到头**的反向迭代器。
    

---

## 4. 常见实现类

在选择 `Deque` 的实现时，通常基于以下两个类：

1. **`ArrayDeque`**（首选）：
    
    - 基于**循环数组**实现。
        
    - **非线程安全**。
        
    - 作为栈使用时比 `Stack` 快，作为队列使用时比 `LinkedList` 快（内存利用率高，无频繁 GC 压力）。
        
    - 不允许插入 `null` 元素。
        
2. **`LinkedList`**：
    
    - 基于**双向链表**实现。
        
    - **非线程安全**。
        
    - 除了 `Deque` 接口，还实现了 `List` 接口。
        
    - 在随机插入/删除时性能较好，但整体内存开销比 `ArrayDeque` 大。
        
    - 允许插入 `null` 元素。
        

> **注意**：如果需要线程安全，可以使用 `Collections.synchronizedDeque(new ArrayDeque<>())` 或者使用 `LinkedBlockingDeque`。

---

## 5. 代码示例

Java

```
Deque<String> deque = new ArrayDeque<>();

// 1. 队列用法
deque.offerLast("A");
deque.offerLast("B");
System.out.println(deque.pollFirst()); // 输出 A

// 2. 栈用法
deque.push("C");
deque.push("D");
System.out.println(deque.pop()); // 输出 D

// 3. 反向遍历
Iterator<String> it = deque.descendingIterator();
while(it.hasNext()) {
    System.out.println(it.next());
}
```

你是正在准备面试，还是在实际项目中遇到了性能优化的问题，需要选择合适的实现类呢？