# 频繁 Full GC 排查 SOP

## 一、先说结论

频繁 Full GC 的核心问题是：

```text
老年代、元空间或堆整体压力过大，导致 JVM 不断做全局回收，但回收效果不好。
```

排查链路：

```text
观察 GC 指标
-> 分析 GC 日志
-> 判断触发原因
-> dump 堆分析对象
-> 修复内存增长或参数问题
```

## 二、先看指标

常用命令：

```bash
jstat -gcutil <pid> 1000
```

关注：

- S0、S1：Survivor 使用率。
- E：Eden 使用率。
- O：Old 使用率。
- M：Metaspace 使用率。
- YGC：Young GC 次数。
- YGCT：Young GC 总耗时。
- FGC：Full GC 次数。
- FGCT：Full GC 总耗时。

如果 FGC 快速增长，就是明显异常。

## 三、常见触发原因

### 1. 老年代空间不足

表现：

- Old 区持续高位。
- Full GC 后 Old 下降不明显。

常见原因：

- 内存泄漏。
- 大量长生命周期对象。
- 缓存无上限。
- 队列积压。

### 2. 大对象直接进入老年代

常见场景：

- 一次性查询大量数据。
- 大数组。
- 大 JSON。
- 文件一次性读入内存。

处理：

- 分页。
- 流式处理。
- 限制导出大小。
- 降低单次批量大小。

### 3. 对象晋升过快

Young GC 后存活对象太多，Survivor 放不下，提前晋升到老年代。

原因：

- 新生代太小。
- 瞬时流量过大。
- 对象生命周期中等偏长。

### 4. 元空间不足

表现：

- Metaspace 使用率高。
- Full GC 由元空间触发。

原因：

- 动态代理类过多。
- 类加载器泄漏。
- 脚本引擎动态生成类。
- 热部署问题。

### 5. System.gc()

代码或第三方库显式调用：

```java
System.gc();
```

可以通过 GC 日志确认是否为 System.gc 触发。

生产可考虑：

```bash
-XX:+DisableExplicitGC
```

但要评估 DirectByteBuffer 等依赖显式 GC 清理的场景。

## 四、GC 日志分析

生产建议开启 GC 日志。

JDK 9+ 示例：

```bash
-Xlog:gc*:file=/data/logs/gc.log:time,uptime,level,tags
```

关注：

- GC 类型。
- 触发原因。
- 回收前后内存变化。
- 暂停时间。
- Full GC 后老年代是否明显下降。

判断标准：

```text
Full GC 后老年代下降明显：可能是容量或晋升压力问题。
Full GC 后老年代下降很少：高度怀疑内存泄漏或长生命周期对象过多。
```

## 五、堆 dump 分析

当 Old 区持续高位时，导出 dump：

```bash
jmap -dump:format=b,file=heap.hprof <pid>
```

MAT 中看：

- Dominator Tree。
- Retained Heap。
- Leak Suspects。
- Path to GC Roots。

重点找：

- 最大对象是谁。
- 谁持有它。
- 是否符合业务预期。

## 六、G1 常见问题

G1 关注：

- Humongous Object。
- Mixed GC 是否及时。
- Remembered Set 开销。
- IHOP 阈值。
- Pause time 目标是否过于激进。

大对象超过 Region 一半会被视为 Humongous Object，可能导致老年代压力。

处理：

- 减少大对象。
- 调整 Region 大小。
- 分批处理数据。

## 七、处理策略

### 代码层面

- 缓存加容量和过期策略。
- 大查询改分页。
- 文件改流式。
- 队列设置上限。
- ThreadLocal remove。
- 监听器注销。

### 参数层面

- 合理设置堆大小。
- 调整新生代比例。
- 调整 Metaspace。
- 选择合适 GC。
- G1 调整暂停目标和 Region。

### 架构层面

- 拆分大任务。
- 异步削峰。
- 限流。
- 扩容。
- 数据冷热分离。

## 八、面试表达模板

```text
我会先用 jstat 看 FGC 是否持续增长，再结合 GC 日志看触发原因和回收效果。
如果 Full GC 后 Old 区下降不明显，优先怀疑内存泄漏或长生命周期对象过多，会 dump 堆用 MAT 看 Dominator Tree 和 GC Roots。
如果是大对象或晋升过快，会看业务是否有大批量查询、导出、队列积压，并结合新生代和 G1 参数调整。
我不会一上来只调大堆，因为那可能只是把 Full GC 延后，并让停顿更长。
```

