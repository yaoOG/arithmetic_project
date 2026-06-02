# 线上 CPU 飙高排查 SOP

## 一、先说结论

CPU 飙高排查核心链路：

```text
top 找进程
-> top -Hp 找线程
-> 线程 ID 转 16 进制
-> jstack 找线程栈
-> 判断代码热点、死循环、锁竞争、GC 或系统调用
```

目标不是“看到 CPU 高”，而是定位：

```text
哪个线程、哪段代码、为什么一直消耗 CPU。
```

## 二、排查步骤

### 1. 找到高 CPU 进程

```bash
top
```

关注：

- `%CPU`
- `%MEM`
- 进程 PID
- load average

如果 Java 进程 CPU 高，记录 PID。

### 2. 找到高 CPU 线程

```bash
top -Hp <pid>
```

找到占用 CPU 高的线程 ID，例如：

```text
12345
```

### 3. 线程 ID 转 16 进制

```bash
printf "%x\n" 12345
```

得到：

```text
3039
```

### 4. 导出线程栈

```bash
jstack <pid> > jstack.log
```

搜索：

```text
nid=0x3039
```

找到对应线程栈。

### 5. 分析线程状态和代码栈

重点看：

- 线程名称。
- `java.lang.Thread.State`。
- 正在执行的方法。
- 是否一直停留在同一行。
- 是否有锁等待。

## 三、常见原因

### 1. 死循环

特征：

- 某个业务线程长时间 RUNNABLE。
- 多次 jstack 栈位置基本不变。
- CPU 单核或多核持续打满。

常见代码：

```java
while (true) {
    // 没有退出条件
}
```

或集合遍历、重试逻辑、状态轮询没有 sleep。

### 2. 频繁 GC

特征：

- GC 线程占用 CPU。
- 应用吞吐下降。
- jstat 显示 GC 次数快速增加。

命令：

```bash
jstat -gcutil <pid> 1000
```

判断：

- YGC 是否频繁。
- FGC 是否增长。
- Old 区是否持续高位。

### 3. 锁竞争严重

特征：

- 大量线程 BLOCKED。
- 少数线程持有锁。
- CPU 可能高，也可能 load 高但 CPU 不高。

jstack 中关注：

```text
BLOCKED (on object monitor)
waiting to lock
locked
```

### 4. 线程池任务过多

特征：

- 业务线程池大量 RUNNABLE。
- 队列积压。
- 下游慢调用。

需要结合：

- 线程池 activeCount。
- queue size。
- rejected count。
- 接口 RT。

### 5. JSON 序列化、正则、加解密热点

特征：

- 栈中出现 Jackson/Fastjson/Gson。
- 正则 Pattern。
- 加密摘要算法。
- 大对象转换。

常见于大响应、复杂日志、批量导出。

## 四、多次采样

不要只看一次 jstack。

建议连续采样：

```bash
jstack <pid> > jstack1.log
sleep 5
jstack <pid> > jstack2.log
sleep 5
jstack <pid> > jstack3.log
```

如果同一线程多次停留在同一业务方法，基本可以定位热点。

## 五、辅助工具

### 1. Arthas

常用命令：

```bash
thread -n 5
dashboard
trace
watch
profiler
```

`thread -n 5` 可以直接查看最耗 CPU 的线程。

### 2. async-profiler

适合生成火焰图，定位 CPU 热点。

### 3. APM

结合 SkyWalking、Pinpoint、Prometheus 查看接口 RT、QPS、错误率。

## 六、处理策略

- 死循环：修复退出条件，加保护阈值。
- 重试风暴：限制重试次数，加退避和熔断。
- GC 导致：分析内存分配和对象生命周期。
- 锁竞争：缩小锁粒度，减少锁内耗时操作。
- 大对象处理：分页、流式处理、异步化。
- 日志过重：降级日志级别，避免大对象 JSON 日志。

## 七、面试表达模板

```text
我会先用 top 找到高 CPU 的 Java 进程，再用 top -Hp 定位具体线程。
把线程 ID 转成 16 进制后，到 jstack 里搜索 nid。
然后连续 dump 几次线程栈，判断线程是否一直卡在同一段业务代码。
如果是业务线程 RUNNABLE，多半是死循环、热点计算或序列化；如果是 GC 线程高，就结合 jstat 和 GC 日志看是否频繁 GC；如果大量 BLOCKED，则看锁竞争。
```

