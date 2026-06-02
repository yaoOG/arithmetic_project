# Redis 分布式锁正确实现

## 一、先说结论

Redis 分布式锁的正确基础写法是：

```text
SET lock_key unique_value NX EX seconds
```

释放锁必须校验 value，并用 Lua 脚本保证原子性：

```lua
if redis.call("get", KEYS[1]) == ARGV[1] then
    return redis.call("del", KEYS[1])
else
    return 0
end
```

一句话：

```text
加锁要同时满足互斥和过期，解锁要防止误删别人的锁。
```

## 二、为什么不能 SETNX 后再 EXPIRE

错误写法：

```text
SETNX lock_key value
EXPIRE lock_key 30
```

问题：

```text
如果 SETNX 成功后服务宕机，EXPIRE 没执行，锁就永不过期。
```

所以加锁和设置过期时间必须是一个原子命令：

```text
SET key value NX EX seconds
```

## 三、为什么 value 必须唯一

假设线程 A 获取锁，业务执行太久，锁过期了。线程 B 随后获取到同一把锁。

如果 A 执行完后直接 `DEL lock_key`，就会把 B 的锁删掉。

所以 value 必须是当前持有者的唯一标识：

```text
requestId / UUID / 线程标识
```

释放锁时必须判断：

```text
只有 value 还是自己的，才能删除。
```

## 四、为什么释放锁要用 Lua

错误写法：

```text
GET lock_key
DEL lock_key
```

问题：

```text
GET 后锁可能过期并被别人重新获取，此时 DEL 会误删别人的锁。
```

Lua 脚本能保证判断和删除在 Redis 内原子执行。

## 五、锁过期时间如何设置

锁过期时间必须大于业务最大执行时间，但不能过长。

过短：

```text
业务没执行完锁就过期，多个线程同时进入临界区。
```

过长：

```text
服务宕机后其他请求等待时间过久。
```

生产建议：

- 根据 P99 或 P999 业务耗时设置。
- 关键业务用 watchdog 自动续期。
- 长任务不要简单依赖固定过期时间。

## 六、Redisson Watchdog 机制

Redisson 获取锁时，如果没有显式指定 leaseTime，会启用 watchdog。

大致机制：

```text
1. 加锁成功，设置默认过期时间。
2. 后台定时任务定期检查当前线程是否仍持有锁。
3. 如果仍持有，自动延长锁过期时间。
4. unlock 时取消续期并释放锁。
```

价值：

```text
避免业务执行时间超过锁过期时间导致锁提前释放。
```

注意：

- 如果业务线程阻塞很久、JVM 长时间 STW、Redis 网络异常，续期仍可能失败。
- watchdog 不是强一致银弹。

## 七、可重入锁原理

Redisson 可重入锁通常用 Redis Hash 表示：

```text
key: lock_name
field: clientId:threadId
value: 重入次数
```

同一线程重复加锁：

```text
计数 +1
```

释放锁：

```text
计数 -1
计数为 0 才真正删除锁
```

## 八、Redis 主从切换风险

Redis 单主异步复制时存在风险：

```text
1. 客户端 A 在 master 加锁成功。
2. 锁还没同步到 slave。
3. master 宕机，slave 晋升为新 master。
4. 客户端 B 在新 master 上也加锁成功。
```

结果：

```text
同一时刻可能有两个客户端认为自己持有锁。
```

所以 Redis 分布式锁适合大多数业务互斥，但不是强一致锁。

如果是金融账务等强一致场景，优先考虑：

- 数据库行锁。
- 乐观锁。
- 状态机。
- Zookeeper/etcd 类强一致协调组件。

## 九、RedLock 争议

RedLock 试图通过多个独立 Redis 节点投票获取锁，降低单点主从切换风险。

但它仍然受到时钟、网络延迟、进程暂停等问题影响，业内对其强一致语义有争议。

面试表达：

```text
RedLock 可以提升可用性和容错，但不能简单宣称它等价于 CP 系统里的强一致锁。
```

## 十、分布式锁使用原则

- 锁粒度尽量小。
- 锁内逻辑尽量短。
- 锁内不要做不可控远程调用。
- 必须设置过期时间。
- 释放锁必须校验 value。
- 关键业务还要有数据库状态机或唯一约束兜底。

## 十一、常见面试追问

### 1. Redis 分布式锁能保证绝对安全吗

不能。单主异步复制和故障切换会带来锁丢失风险。它更适合效率型互斥，不适合作为强一致最终防线。

### 2. 锁过期但业务没执行完怎么办

可以用 Redisson watchdog 续期，或者重新设计任务，把长事务拆短。关键业务仍要靠状态机和幂等兜底。

### 3. 为什么解锁要用 Lua

为了保证“判断 value 是否相等”和“删除 key”两个操作原子执行，避免误删其他线程的锁。

