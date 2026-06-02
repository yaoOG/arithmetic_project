# 线上 OOM 排查 SOP

## 一、先说结论

OOM 排查第一步不是立刻调大内存，而是判断 OOM 类型：

```text
Java heap space
Metaspace
Direct buffer memory
unable to create new native thread
GC overhead limit exceeded
```

不同类型对应完全不同的根因和处理方式。

核心链路：

```text
确认 OOM 类型
-> 保留现场
-> dump 内存或线程
-> MAT/工具分析
-> 找 GC Roots 引用链
-> 修复泄漏或容量问题
```

## 二、常见 OOM 类型

### 1. Java heap space

堆内存不足。

常见原因：

- 内存泄漏。
- 一次性加载大数据。
- 缓存无上限。
- 大对象过多。
- 流量突增导致对象堆积。

### 2. Metaspace

元空间不足。

常见原因：

- 动态生成类过多。
- CGLIB/JDK 代理大量创建。
- 类加载器泄漏。
- 热部署未释放旧 ClassLoader。

### 3. Direct buffer memory

直接内存不足。

常见原因：

- Netty DirectByteBuffer 泄漏。
- NIO buffer 未释放。
- 堆外内存限制太小。

### 4. unable to create new native thread

无法创建本地线程。

常见原因：

- 线程数过多。
- 线程池失控。
- 操作系统线程限制。
- 每个线程栈空间过大。

### 5. GC overhead limit exceeded

JVM 大部分时间在 GC，但回收效果很差。

常见原因：

- 堆快满了。
- 老年代有大量存活对象。
- 内存泄漏。

## 三、保留现场

生产建议提前配置：

```bash
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/data/dump
-XX:ErrorFile=/data/logs/hs_err_pid%p.log
```

OOM 后不要急着重启，先尽量保留：

- heap dump。
- GC 日志。
- 应用日志。
- jstack。
- 监控指标。

如果服务已经严重不可用，要按应急预案先恢复，再离线分析 dump。

## 四、堆 OOM 分析流程

### 1. 导出 heap dump

如果未自动生成，可以手动：

```bash
jmap -dump:format=b,file=heap.hprof <pid>
```

注意：

```text
dump 可能触发 STW，生产执行要谨慎。
```

### 2. 使用 MAT 分析

重点看：

- Leak Suspects。
- Dominator Tree。
- Retained Heap。
- Path to GC Roots。

### 3. 找最大保留对象

关注 Retained Heap 最大的对象，判断它为什么不能被回收。

常见泄漏源：

- static Map。
- ThreadLocal。
- 本地缓存。
- 监听器未注销。
- 队列积压。
- 大 List。
- session 过多。

### 4. 看 GC Roots 引用链

目标：

```text
找到是谁还持有这个对象。
```

例如：

```text
GC Roots
-> static ConcurrentHashMap
-> userCache
-> 大量 User 对象
```

## 五、ThreadLocal 内存泄漏

ThreadLocalMap 的 key 是弱引用，但 value 是强引用。

如果线程长期不销毁，且没有 remove：

```text
Thread
-> ThreadLocalMap
-> Entry
-> value
```

value 可能一直无法释放。

正确用法：

```java
try {
    threadLocal.set(value);
    // business
} finally {
    threadLocal.remove();
}
```

尤其在线程池场景必须 remove。

## 六、线程过多 OOM 排查

错误：

```text
java.lang.OutOfMemoryError: unable to create new native thread
```

排查：

```bash
ps -eLf | grep <pid> | wc -l
jstack <pid>
```

常见原因：

- 每个请求创建线程。
- 线程池无界增长。
- 定时任务重复创建线程池。
- CompletableFuture 默认线程池被误用。

处理：

- 使用有界线程池。
- 复用线程池。
- 限制任务提交。
- 检查 `-Xss`。
- 检查系统 ulimit。

## 七、直接内存 OOM 排查

错误：

```text
java.lang.OutOfMemoryError: Direct buffer memory
```

排查方向：

- Netty 是否使用直接内存。
- 是否有 ByteBuf 未 release。
- `-XX:MaxDirectMemorySize` 配置。
- NMT Native Memory Tracking。

Netty 中重点关注：

```text
ByteBuf retain/release 是否成对。
```

## 八、应急处理

短期恢复：

- 重启实例。
- 临时扩容。
- 降级大流量接口。
- 关闭非核心功能。
- 限制批量任务。
- 调整缓存上限。

长期修复：

- 修复泄漏代码。
- 增加缓存淘汰策略。
- 批量查询改分页。
- 大文件改流式处理。
- 增加监控和告警。

## 九、面试表达模板

```text
我会先确认 OOM 类型，不同 OOM 根因完全不同。
如果是 Java heap space，会保留 heap dump，用 MAT 看 Dominator Tree 和 GC Roots 引用链，定位是缓存、ThreadLocal、队列还是大对象。
如果是 unable to create new native thread，会看线程数和 jstack，排查线程池失控。
如果是 Direct buffer memory，会重点看 Netty 或 NIO 的堆外内存释放。
修复上不会只调大内存，而是结合对象生命周期、缓存上限、批处理方式和监控告警一起处理。
```

