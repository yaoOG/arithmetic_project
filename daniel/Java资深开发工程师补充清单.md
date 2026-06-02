# Java资深开发工程师补充清单

> 目标：把现有的并发、JVM、MySQL、Redis、MQ 笔记继续升级成资深 Java 开发面试和实战可用的知识体系。

## 一、当前知识库已有优势

你的知识库目前已经覆盖了不少高级后端主题：

- Java 并发：线程池、AQS、synchronized、volatile、CAS、ConcurrentHashMap、CompletableFuture 等。
- JVM：GC Roots、引用类型、回收算法、对象回收判断、部分线上内存泄漏问题。
- MySQL：事务、MVCC、redo/undo/binlog、索引、锁、Explain。
- Redis：基础数据结构、List 底层结构。
- MQ：Kafka 高吞吐、零拷贝、顺序性、ISR、RocketMQ 架构、事务消息、顺序消息、延时消息。
- 算法：二叉树和二叉搜索树相关题目。

这些内容已经有明显的“底层机制 + 面试表达”意识，后续重点不是推翻重学，而是补齐框架、分布式、线上排障和项目表达。

## 二、第一优先级：Spring / Spring Boot / Spring Cloud

这是当前最大短板。资深 Java 面试几乎绕不开 Spring 体系，建议优先补。

### 1. Spring 核心机制

- Bean 生命周期完整流程。
- BeanFactory 和 ApplicationContext 的区别。
- BeanDefinition 是什么，什么时候生成。
- 后置处理器：BeanFactoryPostProcessor、BeanPostProcessor。
- Aware 接口的作用。
- InitializingBean、init-method、@PostConstruct 的执行顺序。
- FactoryBean 和普通 Bean 的区别。
- Spring 事件机制：ApplicationEvent、ApplicationListener。

建议输出笔记：

- `Spring Bean 生命周期.md`
- `BeanFactory 和 ApplicationContext 区别.md`
- `BeanPostProcessor 的作用和源码流程.md`
- `FactoryBean 和 BeanFactory 的区别.md`

### 2. 循环依赖

- Spring 如何用三级缓存解决单例循环依赖。
- 为什么构造器注入无法解决循环依赖。
- 为什么 prototype Bean 无法通过三级缓存解决循环依赖。
- AOP 代理对象如何参与循环依赖。
- 三级缓存中 ObjectFactory 的作用。

建议输出笔记：

- `Spring 三级缓存解决循环依赖.md`
- `为什么构造器注入无法解决循环依赖.md`
- `AOP 和循环依赖的关系.md`

### 3. AOP 与动态代理

- JDK 动态代理和 CGLIB 的区别。
- Spring AOP 什么时候用 JDK，什么时候用 CGLIB。
- AOP 代理对象的生成时机。
- 切面、切点、通知、Advisor、Interceptor 的关系。
- 为什么同类方法内部调用会导致 AOP 失效。

建议输出笔记：

- `JDK 动态代理和 CGLIB 的区别.md`
- `Spring AOP 原理.md`
- `为什么同类方法调用会导致 AOP 失效.md`

### 4. Spring 事务

- @Transactional 的实现原理。
- 事务传播行为：REQUIRED、REQUIRES_NEW、NESTED。
- 事务隔离级别和数据库隔离级别的关系。
- 事务失效场景：
  - 方法不是 public。
  - 同类内部调用。
  - 异常被 catch 后没有抛出。
  - 默认只回滚 RuntimeException。
  - 多线程中事务上下文不传播。
- 编程式事务 TransactionTemplate。

建议输出笔记：

- `Spring 事务传播行为.md`
- `@Transactional 失效场景.md`
- `Spring 事务和数据库事务的关系.md`

### 5. Spring Boot 自动配置

- Spring Boot 启动流程。
- @SpringBootApplication 包含哪些注解。
- 自动配置原理。
- 条件装配：@ConditionalOnClass、@ConditionalOnMissingBean 等。
- starter 的设计原理。
- Spring Boot 2 和 Spring Boot 3 的自动配置文件差异。

建议输出笔记：

- `Spring Boot 启动流程.md`
- `Spring Boot 自动配置原理.md`
- `如何自定义一个 starter.md`

### 6. Spring MVC 请求链路

- DispatcherServlet 的核心作用。
- HandlerMapping、HandlerAdapter、ViewResolver。
- 参数解析器 HandlerMethodArgumentResolver。
- 返回值处理器 HandlerMethodReturnValueHandler。
- 拦截器和过滤器的区别。
- 异常处理：@ControllerAdvice、@ExceptionHandler。

建议输出笔记：

- `Spring MVC 请求执行流程.md`
- `过滤器和拦截器的区别.md`
- `Spring MVC 参数解析和返回值处理.md`

### 7. MyBatis

- MyBatis 执行流程。
- Mapper 接口为什么不需要实现类。
- 一级缓存和二级缓存。
- #{} 和 ${} 的区别。
- 插件机制：Interceptor。
- 动态 SQL。
- 分页插件原理。

建议输出笔记：

- `MyBatis 执行流程.md`
- `Mapper 接口代理原理.md`
- `MyBatis 一级缓存和二级缓存.md`
- `MyBatis 插件机制.md`

## 三、第二优先级：分布式与高并发系统设计

这一块决定你能不能从“会背原理”上升到“能设计系统”。

### 1. 接口幂等

- 什么是幂等。
- 哪些接口必须做幂等：支付回调、订单创建、库存扣减、消息消费。
- 幂等实现方式：
  - 唯一索引。
  - token 防重。
  - 状态机。
  - 去重表。
  - Redis setnx。
  - MQ 消费记录表。
- 幂等和防重、防并发的区别。

建议输出笔记：

- `接口幂等设计.md`
- `MQ 消费幂等如何实现.md`
- `支付回调幂等设计.md`

### 2. 分布式锁

- Redis 分布式锁正确写法：`SET key value NX EX seconds`。
- 为什么不能简单用 SETNX 后再 EXPIRE。
- 释放锁为什么要校验 value。
- Lua 脚本保证释放锁原子性。
- 锁续期和 watchdog。
- Redisson 可重入锁原理。
- Redis 主从切换时分布式锁的风险。
- RedLock 的争议。
- Zookeeper 分布式锁原理。

建议输出笔记：

- `Redis 分布式锁正确实现.md`
- `Redisson 看门狗机制.md`
- `Redis 分布式锁和 Zookeeper 分布式锁对比.md`

### 3. 缓存一致性

- Cache Aside Pattern。
- 先删缓存还是先更新数据库。
- 为什么通常推荐“先更新数据库，再删除缓存”。
- 删除缓存失败怎么办。
- 延迟双删的适用场景和局限。
- binlog + Canal 同步缓存。
- 热点 key、缓存击穿、缓存穿透、缓存雪崩。
- 本地缓存和分布式缓存的一致性问题。

建议输出笔记：

- `缓存和数据库一致性.md`
- `缓存穿透击穿雪崩.md`
- `热点 key 问题如何解决.md`

### 4. 限流、熔断、降级

- 限流算法：
  - 固定窗口。
  - 滑动窗口。
  - 漏桶。
  - 令牌桶。
- 单机限流和分布式限流。
- Sentinel 核心概念：资源、规则、限流、熔断、降级。
- 熔断和降级的区别。
- 服务雪崩链路。
- 舱壁隔离和线程池隔离。

建议输出笔记：

- `限流算法总结.md`
- `Sentinel 核心原理.md`
- `熔断和降级的区别.md`

### 5. 分布式事务

- 2PC、3PC、TCC、Saga、本地消息表、事务消息。
- 强一致和最终一致的取舍。
- RocketMQ 事务消息适合解决什么问题。
- 本地消息表如何保证可靠投递。
- 订单、库存、支付场景如何设计最终一致。

建议输出笔记：

- `分布式事务方案对比.md`
- `本地消息表实现最终一致性.md`
- `TCC 和 Saga 的区别.md`

### 6. 分库分表

- 为什么要分库分表。
- 垂直拆分和水平拆分。
- 分片键如何选择。
- 分布式 ID：雪花算法、号段模式、Redis 自增。
- 跨分片查询问题。
- 分页、排序、聚合问题。
- 分布式事务问题。
- 扩容迁移方案。

建议输出笔记：

- `分库分表核心问题.md`
- `分布式 ID 方案对比.md`
- `分库分表后的分页和排序.md`

### 7. 微服务治理

- 注册中心：Nacos、Eureka、Zookeeper。
- 配置中心。
- 服务发现。
- 负载均衡。
- 网关。
- 服务超时和重试。
- 链路追踪。
- 灰度发布。
- 服务版本兼容。

建议输出笔记：

- `微服务调用链路.md`
- `Nacos 注册中心原理.md`
- `服务超时重试和幂等的关系.md`

## 四、第三优先级：线上排障能力

资深开发必须能把问题从现象定位到根因。建议按“现象 -> 指标 -> 命令 -> 判断 -> 修复”写笔记。

### 1. CPU 飙高排查

必备链路：

1. `top` 找到高 CPU 进程。
2. `top -Hp pid` 找到高 CPU 线程。
3. 线程 ID 转十六进制。
4. `jstack pid` 找到对应线程栈。
5. 判断是死循环、频繁 GC、锁竞争还是业务热点。

建议输出笔记：

- `线上 CPU 飙高排查 SOP.md`

### 2. 内存泄漏 / OOM 排查

必备链路：

1. 判断是堆内存、元空间、直接内存还是线程过多。
2. `jmap -dump` 导出 heap dump。
3. MAT 分析 dominator tree。
4. 查看到 GC Roots 的引用链。
5. 判断是否为静态集合、ThreadLocal、监听器、缓存未清理、大对象堆积。

建议输出笔记：

- `线上 OOM 排查 SOP.md`
- `ThreadLocal 内存泄漏.md`
- `Direct Memory OOM 排查.md`

### 3. Full GC 频繁排查

需要掌握：

- Young GC、Old GC、Full GC 的区别。
- 晋升失败。
- 大对象直接进入老年代。
- 内存泄漏导致老年代持续增长。
- 元空间触发 Full GC。
- GC 日志如何看。
- G1 常见参数和调优思路。
- ZGC、Shenandoah 的适用场景。

建议输出笔记：

- `频繁 Full GC 排查 SOP.md`
- `G1 GC 日志如何分析.md`
- `CMS G1 ZGC 对比.md`

### 4. 线程池问题排查

需要掌握：

- 活跃线程数。
- 队列长度。
- 拒绝次数。
- 任务耗时。
- 下游连接池是否先打满。
- 线程池隔离。
- 动态线程池原理。
- 线程池参数不是靠公式一次算准，而是靠压测和监控持续调整。

建议输出笔记：

- `线程池打满排查 SOP.md`
- `线程池监控指标.md`

### 5. 慢 SQL 排查

需要掌握：

- 慢查询日志。
- Explain 字段：type、key、rows、Extra。
- Using filesort、Using temporary。
- 索引失效场景。
- 回表。
- 覆盖索引。
- 联合索引最左前缀。
- 深分页优化。
- 大事务和锁等待。

建议输出笔记：

- `慢 SQL 排查 SOP.md`
- `Explain 字段详解.md`
- `深分页优化方案.md`

## 五、第四优先级：Java 基础深水区

这些知识点常被面试官用来连接 JVM、Spring、框架源码。

### 1. 类加载机制

- 类加载过程：加载、验证、准备、解析、初始化。
- 双亲委派模型。
- 为什么要双亲委派。
- 如何打破双亲委派。
- SPI 机制为什么能打破双亲委派。
- Tomcat 类加载器。
- 热部署和类卸载。

建议输出笔记：

- `Java 类加载机制.md`
- `双亲委派模型.md`
- `SPI 机制原理.md`

### 2. 反射、注解、动态代理

- 反射的基本原理和性能问题。
- 注解如何被读取。
- 运行时注解和编译期注解。
- JDK 动态代理。
- CGLIB。
- ByteBuddy。
- Spring、MyBatis、RPC 框架如何使用代理。

建议输出笔记：

- `Java 反射机制.md`
- `注解原理.md`
- `动态代理总结.md`

### 3. 集合框架

- HashMap put 流程。
- HashMap 扩容机制。
- HashMap 为什么线程不安全。
- ArrayList 和 LinkedList。
- CopyOnWriteArrayList。
- BlockingQueue。
- PriorityQueue。
- TreeMap 和红黑树。

建议输出笔记：

- `HashMap 底层原理.md`
- `ArrayList 和 LinkedList 的区别.md`
- `BlockingQueue 总结.md`

### 4. NIO / 网络编程

- BIO、NIO、AIO 的区别。
- Channel、Buffer、Selector。
- 多路复用：select、poll、epoll。
- Reactor 模型。
- Netty 线程模型。
- 粘包拆包。
- 零拷贝。

建议输出笔记：

- `Java NIO 核心原理.md`
- `Reactor 模型.md`
- `Netty 线程模型.md`

## 六、第五优先级：算法补齐路线

目前算法笔记集中在二叉树，建议按题型补齐。

### 必补题型

- 数组和双指针。
- 链表。
- 栈和队列。
- 哈希表。
- 二分查找。
- 滑动窗口。
- 回溯。
- 动态规划。
- 堆。
- 图：BFS、DFS、拓扑排序。

### 推荐笔记结构

每道题建议统一写成：

1. 题目链接。
2. 题目类型。
3. 核心思路。
4. 边界条件。
5. Java 代码。
6. 时间复杂度和空间复杂度。
7. 相似题。

建议输出目录：

- `算法/数组`
- `算法/链表`
- `算法/哈希`
- `算法/二分`
- `算法/滑动窗口`
- `算法/动态规划`
- `算法/回溯`
- `算法/图`

## 七、建议的 30 天补强计划

### 第 1 周：Spring 核心

- Bean 生命周期。
- 三级缓存循环依赖。
- AOP 原理。
- @Transactional 原理和失效场景。
- Spring MVC 请求链路。

产出目标：至少 8 篇 Spring 笔记。

### 第 2 周：分布式高并发

- 接口幂等。
- Redis 分布式锁。
- 缓存一致性。
- 限流熔断降级。
- 分布式事务。

产出目标：至少 8 篇系统设计笔记。

### 第 3 周：线上排障

- CPU 飙高。
- OOM。
- Full GC。
- 线程池打满。
- 慢 SQL。
- MQ 消费堆积。

产出目标：至少 6 篇 SOP 型笔记。

### 第 4 周：Java 基础深水区 + 算法

- 类加载。
- SPI。
- HashMap。
- NIO。
- Netty 线程模型。
- 每天 2 道算法题。

产出目标：至少 5 篇 Java 基础笔记 + 14 道算法题。

## 八、面试表达升级建议

每个知识点尽量从“背概念”升级成下面的结构：

```text
1. 先说结论。
2. 解释底层机制。
3. 说明适用场景。
4. 说一个生产坑点。
5. 给出取舍或优化方案。
```

示例：

```text
Redis 分布式锁不能只用 SETNX。
正确做法是 SET key value NX EX seconds，因为加锁和过期时间必须原子化。
释放锁时必须校验 value，避免误删别人的锁。
生产中一般用 Redisson，因为它支持可重入、自动续期和 Lua 原子释放。
但 Redis 主从切换时仍然可能存在锁安全风险，如果业务强一致要求极高，可以考虑 Zookeeper 锁或数据库悲观锁。
```

## 九、最终目标画像

补完这些内容后，你的知识库应该能支撑下面几类问题：

- 讲得清 Java 并发和 JVM 底层。
- 看得懂 Spring、MyBatis 等框架源码主链路。
- 能设计高并发订单、支付、库存、消息系统。
- 能处理线上 CPU、内存、GC、慢 SQL、线程池、MQ 堆积问题。
- 能用项目经验把技术点串起来，而不是孤立背题。

