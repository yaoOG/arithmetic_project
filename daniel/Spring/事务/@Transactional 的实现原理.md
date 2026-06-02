`@Transactional` 的核心实现原理：**基于 Spring AOP 生成代理，在方法调用前后开启、提交或回滚事务**。

典型流程是：

1. Spring 启动时扫描到 `@Transactional`
2. 为对应 Bean 创建代理对象
3. 外部调用代理对象的方法
4. 代理拦截方法调用
5. 根据事务配置开启事务
6. 执行业务方法
7. 方法正常返回则提交事务
8. 方法抛出异常则按规则回滚事务

简化理解：

```java
@Transactional
public void createOrder() {
    insertOrder();
    deductStock();
}
```

实际执行时类似：

```java
public void proxyCreateOrder() {
    TransactionStatus status = transactionManager.begin();

    try {
        target.createOrder();
        transactionManager.commit(status);
    } catch (Exception e) {
        transactionManager.rollback(status);
        throw e;
    }
}
```

不过真实源码里不是这么写死的，而是通过这些组件协作完成：

| 组件 | 作用 |
|---|---|
| `@Transactional` | 声明事务规则 |
| `TransactionInterceptor` | AOP 拦截器，负责事务增强 |
| `TransactionAttributeSource` | 解析事务注解属性 |
| `PlatformTransactionManager` | 真正负责开启、提交、回滚事务 |
| `TransactionStatus` | 保存当前事务状态 |
| `DataSourceTransactionManager` | JDBC / MyBatis 常用事务管理器 |
| `JpaTransactionManager` | JPA 常用事务管理器 |

执行链大致是：

```text
代理对象
  -> TransactionInterceptor
    -> PlatformTransactionManager.getTransaction()
    -> 执行业务方法
    -> commit() / rollback()
```

回滚规则也很重要。

默认情况下，Spring 只会对：

```text
RuntimeException
Error
```

进行回滚。

对普通受检异常，比如：

```java
IOException
SQLException
```

默认不回滚，除非显式指定：

```java
@Transactional(rollbackFor = Exception.class)
public void createOrder() {
}
```

