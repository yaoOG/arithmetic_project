# Java 集合框架总结

## 一、先说结论

Java 集合面试不要只背接口名，要能回答三个问题：

```text
底层结构是什么？
适合什么场景？
线程安全性如何？
```

核心体系：

```text
Collection
├── List
├── Set
└── Queue

Map
├── HashMap
├── LinkedHashMap
├── TreeMap
└── ConcurrentHashMap
```

## 二、List

### 1. ArrayList

底层：

```text
动态数组 Object[]
```

特点：

- 随机访问快，O(1)。
- 尾部追加快，均摊 O(1)。
- 中间插入/删除慢，需要移动元素。
- 非线程安全。

扩容：

```text
容量不够时扩容为原来的约 1.5 倍。
```

适合：

- 读多写少。
- 按下标访问。
- 尾部追加。

### 2. LinkedList

底层：

```text
双向链表
```

特点：

- 随机访问慢，O(n)。
- 已定位节点后插入删除快。
- 可作为队列/双端队列使用。

注意：

```text
不要简单说 LinkedList 插入删除一定比 ArrayList 快。
如果需要先按下标定位，LinkedList 仍然要 O(n) 遍历。
```

### 3. CopyOnWriteArrayList

底层：

```text
写时复制数组
```

写操作：

```text
复制新数组 -> 修改新数组 -> 替换引用
```

适合：

- 读多写极少。
- 监听器列表。
- 配置快照。

缺点：

- 写成本高。
- 内存占用高。
- 读到的是弱一致快照。

## 三、Set

### 1. HashSet

底层：

```text
HashMap 的 key
```

特点：

- 元素唯一。
- 无序。
- 依赖 hashCode 和 equals。

### 2. LinkedHashSet

底层：

```text
LinkedHashMap
```

特点：

- 保持插入顺序。

### 3. TreeSet

底层：

```text
TreeMap，红黑树
```

特点：

- 有序。
- 操作 O(logn)。
- 依赖 Comparable 或 Comparator。

## 四、Map

### 1. HashMap

底层：

```text
数组 + 链表 + 红黑树
```

特点：

- 查询和插入平均 O(1)。
- 非线程安全。
- key 可为 null。

### 2. LinkedHashMap

特点：

- 在 HashMap 基础上维护双向链表。
- 支持插入顺序或访问顺序。

典型用途：

- LRU 缓存。

示例：

```java
new LinkedHashMap<K, V>(16, 0.75f, true) {
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
};
```

### 3. TreeMap

底层：

```text
红黑树
```

特点：

- key 有序。
- 查询、插入、删除 O(logn)。
- 支持范围查询。

### 4. ConcurrentHashMap

特点：

- 线程安全。
- JDK 8 使用 CAS + synchronized + volatile。
- 不允许 null key 和 null value。

为什么不允许 null：

```text
并发场景下无法区分 get 返回 null 是 key 不存在，还是 value 本身为 null。
```

## 五、Queue

### 1. ArrayDeque

底层：

```text
循环数组
```

适合：

- 栈。
- 队列。
- 双端队列。

推荐替代 Stack：

```java
Deque<Integer> stack = new ArrayDeque<>();
```

### 2. PriorityQueue

底层：

```text
堆，默认小顶堆
```

适合：

- Top K。
- 优先级调度。
- 合并有序数据。

### 3. BlockingQueue

常见实现：

- ArrayBlockingQueue。
- LinkedBlockingQueue。
- PriorityBlockingQueue。
- DelayQueue。
- SynchronousQueue。

线程池中的 workQueue 就是 BlockingQueue。

## 六、集合线程安全选择

| 场景 | 推荐 |
|---|---|
| 单线程 Map | HashMap |
| 并发 Map | ConcurrentHashMap |
| 读多写少 List | CopyOnWriteArrayList |
| 阻塞队列 | BlockingQueue |
| 有序 Map | TreeMap |
| LRU | LinkedHashMap / Caffeine |

## 七、常见面试追问

### 1. ArrayList 和 LinkedList 怎么选

绝大多数业务场景优先 ArrayList。只有频繁在已知节点附近插入删除，LinkedList 才有优势。

### 2. HashMap 和 ConcurrentHashMap 区别

HashMap 非线程安全；ConcurrentHashMap 通过并发控制保证线程安全，并针对读写做了优化。

### 3. 为什么不推荐 Vector 和 Hashtable

它们是早期同步容器，方法级 synchronized 粒度粗，性能和设计都不如现代并发集合。

## 八、生产经验

- 集合初始化时尽量指定容量。
- 不要在遍历集合时直接结构性修改，除非用 Iterator remove。
- Map key 不要使用可变对象。
- 大集合要关注内存占用。
- 并发场景不要用 Collections.synchronizedMap 作为首选，优先 ConcurrentHashMap。

