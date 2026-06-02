# MyBatis 执行流程和 Mapper 代理原理

## 一、先说结论

MyBatis 的核心不是“写 SQL 的工具”，而是：

```text
Mapper 接口
-> 动态代理
-> MappedStatement
-> Executor
-> StatementHandler
-> JDBC
-> ResultSetHandler
-> Java 对象
```

Mapper 接口没有实现类还能运行，是因为 MyBatis 在运行时为接口创建了 JDK 动态代理。

## 二、核心组件

| 组件 | 作用 |
|---|---|
| SqlSessionFactory | 创建 SqlSession |
| SqlSession | 执行 SQL 的门面 |
| MapperProxy | Mapper 接口动态代理 |
| MappedStatement | 一条 SQL 映射的完整元数据 |
| Executor | 执行器，负责缓存和 SQL 调度 |
| StatementHandler | 创建和执行 JDBC Statement |
| ParameterHandler | 设置 SQL 参数 |
| ResultSetHandler | 处理结果集 |
| TypeHandler | Java 类型和 JDBC 类型转换 |

## 三、Mapper 接口为什么不需要实现类

示例：

```java
public interface UserMapper {
    User selectById(Long id);
}
```

MyBatis 会为 `UserMapper` 创建代理对象。调用方法时：

```text
userMapper.selectById(1L)
-> MapperProxy.invoke()
-> 根据接口名 + 方法名找到 MappedStatement
-> SqlSession.selectOne()
-> Executor 执行 SQL
```

MappedStatement 的 id 通常是：

```text
com.example.UserMapper.selectById
```

所以 Mapper XML 的 namespace 必须和接口全限定名匹配。

## 四、一次查询的执行流程

```text
1. 业务代码调用 Mapper 方法。
2. MapperProxy 拦截方法调用。
3. 根据方法定位 MappedStatement。
4. SqlSession 调用 Executor。
5. Executor 先处理一级缓存。
6. 生成 BoundSql，得到最终 SQL 和参数映射。
7. StatementHandler 创建 PreparedStatement。
8. ParameterHandler 设置参数。
9. JDBC 执行 SQL。
10. ResultSetHandler 把 ResultSet 映射成 Java 对象。
11. 返回结果。
```

## 五、#{} 和 ${} 的区别

### #{}

使用 PreparedStatement 参数占位：

```sql
select * from user where id = #{id}
```

最终类似：

```sql
select * from user where id = ?
```

优点：

- 防 SQL 注入。
- 类型安全。
- 可复用预编译。

### ${}

直接字符串拼接：

```sql
order by ${column}
```

风险：

- SQL 注入。
- 参数不可控。

适用场景：

- 动态表名。
- 动态字段名。
- order by 字段。

生产建议：

```text
能用 #{} 就不用 ${}。
必须用 ${} 时，要做白名单校验。
```

## 六、动态 SQL

常见标签：

- `<if>`
- `<where>`
- `<set>`
- `<trim>`
- `<foreach>`
- `<choose>`

`<foreach>` 常用于批量查询：

```xml
<foreach collection="ids" item="id" open="(" separator="," close=")">
    #{id}
</foreach>
```

注意：

- 大批量 `IN` 查询要控制数量。
- 批量写入要注意 SQL 长度和事务大小。

## 七、Executor 类型

### SIMPLE

每次执行都创建新的 Statement。

### REUSE

复用 Statement。

### BATCH

批处理执行更新语句。

生产注意：

```text
BATCH 适合批量写，但要控制批次大小，并明确 flushStatements 时机。
```

## 八、MyBatis 和 Spring 事务关系

MyBatis 本身能管理事务，但在 Spring 项目中通常交给 Spring 管理。

典型组合：

```text
@Transactional
-> DataSourceTransactionManager
-> SqlSessionTemplate
-> JDBC Connection 绑定到当前线程
```

注意：

```text
同一个事务内的多次 Mapper 调用，应该复用同一个数据库连接。
```

## 九、生产经验

- Mapper XML namespace 必须和 Mapper 接口全限定名一致。
- SQL id 必须和方法名对应。
- 复杂 SQL 优先写 XML，便于审查和优化。
- `${}` 必须白名单。
- 大结果集不要一次性查入内存，考虑分页、游标、流式查询。
- 批处理要控制事务大小，避免 undo log、锁持有、内存压力过大。

## 十、参考

- MyBatis Official Docs  
  https://mybatis.org/mybatis-3/

