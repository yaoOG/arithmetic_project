# Spring 事务传播和失效场景

## 一、先说结论

`@Transactional` 的本质是 Spring AOP + 事务拦截器：

```text
调用代理对象
-> TransactionInterceptor
-> 获取事务属性
-> 通过 PlatformTransactionManager 开启/加入/挂起事务
-> 执行业务方法
-> 根据异常决定提交或回滚
```

资深开发要掌握三件事：

- 事务是否生效，首先看调用有没有经过 Spring 代理。
- 传播行为决定“当前方法如何处理已有事务”。
- 回滚规则默认只对 RuntimeException 和 Error 回滚。

## 二、Spring 事务核心组件

| 组件 | 作用 |
|---|---|
| `@Transactional` | 声明事务属性 |
| `TransactionInterceptor` | 事务拦截器，负责增强方法调用 |
| `PlatformTransactionManager` | 事务管理器抽象 |
| `TransactionAttributeSource` | 解析事务注解元数据 |
| `TransactionStatus` | 当前事务状态 |

常见事务管理器：

- `DataSourceTransactionManager`：JDBC、MyBatis 常用。
- `JpaTransactionManager`：JPA 常用。
- `JtaTransactionManager`：JTA/XA 分布式事务，业务系统中相对少见。

## 三、事务传播行为

### 1. REQUIRED

默认传播行为。

```text
当前有事务：加入当前事务。
当前没有事务：新建事务。
```

典型场景：大多数业务方法。

### 2. REQUIRES_NEW

```text
总是新建一个事务。
如果当前已有事务，先挂起外层事务。
```

典型场景：

- 记录操作日志。
- 保存失败原因。
- 某些需要独立提交的审计记录。

注意：

```text
内层 REQUIRES_NEW 提交后，即使外层事务回滚，内层事务也不会回滚。
```

### 3. NESTED

```text
如果当前有事务，在当前事务内创建一个保存点。
如果当前没有事务，行为类似 REQUIRED。
```

内层异常可以回滚到保存点，不一定导致整个外层事务回滚。

注意：

- 依赖数据库保存点 Savepoint。
- 常见于 `DataSourceTransactionManager`。
- 和 `REQUIRES_NEW` 不同，`NESTED` 仍属于外层物理事务的一部分。

### 4. SUPPORTS

```text
当前有事务：加入事务。
当前没有事务：非事务执行。
```

适合可有可无的只读查询。

### 5. NOT_SUPPORTED

```text
以非事务方式执行。
如果当前有事务，挂起当前事务。
```

适合不希望长事务包住的耗时非事务操作。

### 6. MANDATORY

```text
必须在已有事务中执行。
如果当前没有事务，直接抛异常。
```

适合强制要求上游控制事务边界的底层方法。

### 7. NEVER

```text
必须在非事务环境执行。
如果当前存在事务，直接抛异常。
```

较少使用。

## 四、事务隔离级别

`@Transactional(isolation = Isolation.DEFAULT)` 默认使用数据库自身隔离级别。

MySQL InnoDB 默认通常是 RR，可重复读。

常见隔离级别：

- READ_UNCOMMITTED
- READ_COMMITTED
- REPEATABLE_READ
- SERIALIZABLE

注意：

```text
Spring 的隔离级别最终要落到数据库连接上，数据库不支持或配置不一致时，不能只看注解自嗨。
```

## 五、回滚规则

默认规则：

```text
RuntimeException 回滚
Error 回滚
受检异常 checked exception 不回滚
```

如果希望受检异常也回滚：

```java
@Transactional(rollbackFor = Exception.class)
public void createOrder() throws Exception {
    // ...
}
```

如果异常被 catch 后吞掉，事务拦截器感知不到异常，会正常提交：

```java
@Transactional
public void createOrder() {
    try {
        deductStock();
    } catch (Exception e) {
        log.error("deduct failed", e);
    }
}
```

如果确实要 catch，又希望回滚：

```java
TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
```

但更推荐重新抛出业务异常，让事务边界更清晰。

## 六、常见事务失效场景

### 1. 同类方法内部调用

```java
public void outer() {
    this.inner();
}

@Transactional
public void inner() {
}
```

`this.inner()` 绕过代理，事务不生效。

### 2. 方法不是 public

Spring 基于代理的声明式事务，通常要求事务方法是可被代理调用的 public 方法。

非 public 方法即使标注 `@Transactional`，也容易不符合预期。

### 3. 异常被 catch 吞掉

事务拦截器看不到异常，就会提交。

### 4. 抛出 checked exception 但没配置 rollbackFor

默认不回滚。

### 5. 数据库引擎不支持事务

例如 MySQL MyISAM 不支持事务。

### 6. 手动 new 对象

手动创建的对象不是 Spring Bean，不会被代理。

### 7. 多线程中事务上下文不传播

事务上下文通常绑定在线程本地变量中。新线程不会自动继承父线程事务。

```java
@Transactional
public void order() {
    CompletableFuture.runAsync(() -> updateStock());
}
```

`updateStock()` 不在外层事务中。

### 8. final 类或 final 方法

CGLIB 无法增强 final 方法。

## 七、事务边界设计

好的事务边界应该满足：

- 尽量短。
- 只包住数据库一致性需要的部分。
- 不包远程调用、MQ 发送、大文件处理等长耗时操作。
- 不把用户交互等待放进事务。

反例：

```text
开启事务
-> 调第三方支付接口
-> 调库存 RPC
-> 写数据库
-> 提交事务
```

这会导致数据库连接和锁持有时间过长。

更好的做法：

- 本地事务只保证本地状态一致。
- 跨服务一致性通过事务消息、本地消息表、TCC、Saga 等方案处理。

## 八、常见面试追问

### 1. REQUIRED 和 REQUIRES_NEW 的区别

`REQUIRED` 会加入外层事务，内外通常同生共死。`REQUIRES_NEW` 会挂起外层事务并开启新事务，内层可以独立提交或回滚。

### 2. NESTED 和 REQUIRES_NEW 的区别

`NESTED` 基于保存点，仍在外层物理事务中。`REQUIRES_NEW` 是独立物理事务。

### 3. @Transactional 为什么会失效

本质原因通常是没经过代理、异常规则不匹配、事务资源不支持、事务上下文不在当前线程。

## 九、参考

- Spring Framework Reference: Transaction Management  
  https://docs.spring.io/spring-framework/reference/data-access/transaction.html
- Transactional Javadoc  
  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/annotation/Transactional.html

