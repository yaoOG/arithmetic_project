另外，`@Transactional` 经常失效的原因主要有这些：

1. **方法内部自调用**

```java
public void a() {
    b(); // 没有经过代理对象
}

@Transactional
public void b() {
}
```

因为 `b()` 是通过 `this.b()` 调用的，没有走 Spring AOP 代理，所以事务不会生效。

2. **方法不是 public**

基于代理的事务通常要求事务方法是 `public`。

3. **对象不是 Spring Bean**

自己 `new` 出来的对象不受 Spring 管理，事务自然不会生效。

4. **异常被捕获没有抛出**

```java
@Transactional
public void save() {
    try {
        doSave();
    } catch (Exception e) {
        // 吃掉异常
    }
}
```

Spring 感知不到异常，就会提交事务。

5. **数据库引擎不支持事务**

比如 MySQL 的 MyISAM 不支持事务，InnoDB 才支持。

一句话总结：

`@Transactional` 不是直接修改方法本身，而是 Spring 通过 AOP 代理在方法外面包了一层事务逻辑，由 `TransactionInterceptor` 调用 `PlatformTransactionManager` 完成事务的开启、提交和回滚。