# MyBatis 一级缓存和二级缓存

## 一、先说结论

MyBatis 有两级缓存：

```text
一级缓存：SqlSession 级别，默认开启。
二级缓存：Mapper namespace 级别，默认需要显式开启。
```

资深开发需要明确：

- 一级缓存只在同一个 SqlSession 内有效。
- Spring 集成 MyBatis 后，普通非事务方法中一级缓存存在感很弱。
- 二级缓存有数据一致性风险，互联网业务中通常谨慎使用，更多依赖 Redis 等外部缓存。

## 二、一级缓存

一级缓存作用域是 `SqlSession`。

同一个 SqlSession 内，执行相同查询，MyBatis 可以直接从本地缓存返回结果。

缓存 key 通常和以下因素有关：

- MappedStatement id。
- SQL。
- 参数。
- RowBounds。
- 环境信息。

## 三、一级缓存什么时候失效

- SqlSession 关闭。
- 执行 commit。
- 执行 rollback。
- 执行 update、insert、delete。
- 手动 clearCache。
- 查询条件不同。

注意：

```text
MyBatis 执行写操作后会清空一级缓存，避免同一个 SqlSession 内读到旧数据。
```

## 四、Spring 环境下一级缓存的特点

在 Spring + MyBatis 中，常用的是 `SqlSessionTemplate`。

如果没有事务：

```text
每次 Mapper 方法调用可能获取、使用、关闭一个 SqlSession。
```

因此一级缓存通常很难跨 Mapper 方法调用复用。

如果有事务：

```text
SqlSession 会绑定到当前线程，同一事务内多次 Mapper 调用可能共享一级缓存。
```

所以在 Spring 项目中讨论一级缓存，要结合事务边界。

## 五、二级缓存

二级缓存作用域是 Mapper namespace。

开启方式：

```xml
<cache/>
```

实体对象通常需要可序列化，具体取决于缓存实现。

二级缓存可以跨 SqlSession 共享，但它的可见性通常发生在事务提交后。

## 六、二级缓存的问题

### 1. 数据一致性风险

如果多个 Mapper 操作同一张表，只开启某一个 namespace 的缓存，容易出现 A Mapper 更新了数据，但 B Mapper 的缓存没清理。

### 2. 维度太粗

二级缓存按 namespace 管理，复杂业务下很难做到精准失效。

### 3. 分布式环境不友好

本地二级缓存无法天然跨 JVM 节点一致。

### 4. 和业务缓存边界重叠

互联网业务一般会使用 Redis/Caffeine 等更可控的缓存方案。

## 七、生产建议

- 一级缓存默认保留即可，但不要依赖它做业务缓存。
- 二级缓存慎用，尤其是多表关联、多 Mapper 操作同表、高一致性要求场景。
- 真正的业务缓存建议使用 Redis 或本地缓存，并设计明确的失效策略。
- 如果使用二级缓存，要确保同一数据修改路径能正确清理相关 namespace。

## 八、常见面试追问

### 1. 一级缓存为什么会产生脏读吗

一级缓存只在 SqlSession 内有效。写操作会清空一级缓存，一般不会在同一个 SqlSession 内读到自己已修改前的旧值。但如果数据库被其他事务修改，当前 SqlSession 的一级缓存可能仍返回旧对象，所以不能把它当强一致缓存。

### 2. 二级缓存为什么不推荐大规模使用

因为 namespace 粒度和业务数据边界往往不一致，复杂业务下缓存失效很难维护，容易出现数据不一致。

### 3. Spring 中一级缓存什么时候有效

通常在同一个事务内更容易有效，因为 SqlSession 会绑定到当前线程。

## 九、参考

- MyBatis Official Docs: Cache  
  https://mybatis.org/mybatis-3/sqlmap-xml.html#cache

