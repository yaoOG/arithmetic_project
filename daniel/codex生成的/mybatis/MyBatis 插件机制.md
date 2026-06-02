# MyBatis 插件机制

## 一、先说结论

MyBatis 插件机制本质是拦截器 + 动态代理。

它允许我们拦截 MyBatis 核心对象的方法调用：

- Executor
- StatementHandler
- ParameterHandler
- ResultSetHandler

常见用途：

- 分页插件。
- SQL 审计。
- 慢 SQL 统计。
- 数据权限。
- 多租户字段追加。
- SQL 改写。

## 二、插件能拦截哪些对象

MyBatis 官方插件点主要有四类：

| 对象 | 作用 |
|---|---|
| Executor | SQL 执行调度、缓存、事务相关 |
| StatementHandler | 创建 Statement、执行 SQL |
| ParameterHandler | 设置参数 |
| ResultSetHandler | 处理结果集 |

生产中分页插件常拦截：

```text
Executor.query
或 StatementHandler.prepare
```

## 三、插件基本结构

```java
@Intercepts({
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
    )
})
public class SqlLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 前置逻辑
        Object result = invocation.proceed();
        // 后置逻辑
        return result;
    }
}
```

三个核心元素：

- `@Intercepts`：声明拦截规则。
- `@Signature`：指定拦截哪个类的哪个方法。
- `Invocation.proceed()`：继续执行原方法。

## 四、底层原理

MyBatis 创建核心对象时，会经过插件包装：

```text
原始对象
-> plugin.wrap()
-> 动态代理对象
```

方法调用时：

```text
代理对象
-> Interceptor.intercept()
-> invocation.proceed()
-> 原始方法
```

如果多个插件都匹配，会形成代理链。

## 五、分页插件原理

分页插件一般做两件事：

### 1. 改写原 SQL

例如 MySQL：

```sql
select * from user where status = 1
```

改写为：

```sql
select * from user where status = 1 limit ?, ?
```

### 2. 查询总数

生成 count SQL：

```sql
select count(*) from (原 SQL) tmp
```

注意：

- 复杂 SQL 的 count 改写可能不准确或性能差。
- 深分页仍然慢，limit offset 大时数据库需要扫描并丢弃大量记录。
- 深分页应考虑基于游标、id 范围、搜索引擎或业务侧翻页方案。

## 六、插件开发风险

### 1. SQL 改写风险

SQL 语法复杂，简单字符串拼接容易出错。生产级插件需要使用 SQL parser 或限制使用场景。

### 2. 顺序风险

多个插件同时存在时，执行顺序会影响结果。

例如：

```text
数据权限插件
分页插件
SQL 日志插件
```

不同顺序可能记录到不同 SQL 或生成不同分页结果。

### 3. 性能风险

插件拦截的是所有匹配 SQL 的主链路，不能做重逻辑。

### 4. 兼容风险

MyBatis 版本升级可能导致内部对象结构变化，反射访问内部字段的插件容易出问题。

## 七、生产经验

- 能用成熟插件就不要手写复杂 SQL 改写。
- 自研插件要限制适用范围，避免全局误伤。
- 插件里必须保留原异常堆栈，不要吞异常。
- SQL 日志要注意脱敏。
- 多租户、数据权限类插件一定要有旁路和压测验证。
- 分页插件不能解决深分页本质问题。

## 八、常见面试追问

### 1. MyBatis 插件为什么只能拦截四类对象

因为 MyBatis 只在这些核心对象创建时提供了插件包装入口。

### 2. 分页插件为什么会影响 SQL 性能

它通常会额外执行 count SQL，并对原 SQL 做 limit 改写。复杂查询下 count 可能很重，深分页也无法避免大量扫描。

### 3. 插件和 Spring AOP 有什么区别

Spring AOP 拦截 Spring Bean 方法调用；MyBatis 插件拦截 MyBatis 内部核心对象方法调用，位置更靠近 SQL 执行链路。

## 九、参考

- MyBatis Official Docs: Plugins  
  https://mybatis.org/mybatis-3/configuration.html#plugins

