# 慢 SQL 排查 SOP

## 一、先说结论

慢 SQL 排查核心链路：

```text
慢查询日志定位 SQL
-> Explain 看执行计划
-> 判断索引、扫描行数、排序、临时表
-> 结合表结构和数据分布
-> 优化索引或改写 SQL
-> 压测验证
```

不要只看 SQL 表面，要结合：

- 数据量。
- 区分度。
- 索引结构。
- 执行计划。
- 锁等待。
- 返回行数。

## 二、慢查询日志

开启慢查询后关注：

- SQL。
- 执行时间。
- 扫描行数。
- 返回行数。
- 是否使用索引。

常见配置：

```sql
show variables like 'slow_query_log';
show variables like 'long_query_time';
```

## 三、Explain 核心字段

### 1. type

从好到差大致：

```text
system > const > eq_ref > ref > range > index > ALL
```

重点警惕：

- `ALL`：全表扫描。
- `index`：全索引扫描。

### 2. key

实际使用的索引。

如果为 NULL，说明没用上索引。

### 3. rows

预估扫描行数。

rows 越大，通常成本越高。

### 4. filtered

过滤比例。

filtered 低说明扫描了很多数据但过滤掉大部分。

### 5. Extra

重点关注：

- Using where。
- Using index。
- Using index condition。
- Using filesort。
- Using temporary。

## 四、索引失效常见原因

- 对索引列使用函数。
- 隐式类型转换。
- like 左模糊。
- 联合索引不满足最左前缀。
- or 条件部分字段无索引。
- 范围查询后面的联合索引列利用受限。
- 低区分度字段不适合单独建索引。
- order by 和 where 索引不匹配。

示例：

```sql
where date(create_time) = '2026-01-01'
```

会导致索引列被函数包裹，难以有效利用索引。

改写：

```sql
where create_time >= '2026-01-01 00:00:00'
  and create_time <  '2026-01-02 00:00:00'
```

## 五、联合索引设计

原则：

```text
等值条件在前，范围条件靠后，排序字段尽量兼容 order by。
```

例如：

```sql
where user_id = ?
  and status = ?
  and create_time between ? and ?
order by create_time desc
```

可考虑：

```sql
(user_id, status, create_time)
```

注意：

- 区分度高的字段通常更适合放前面。
- 但更重要的是匹配查询模式。
- 不能脱离 SQL 场景谈索引顺序。

## 六、回表和覆盖索引

二级索引叶子节点存主键值。查询二级索引不能覆盖的列时，需要回主键索引查整行。

覆盖索引：

```text
查询需要的列都在索引中。
```

Explain Extra 可能出现：

```text
Using index
```

适合高频查询，但不要为了覆盖索引无限加宽索引。

## 七、深分页优化

问题 SQL：

```sql
select * from order_info
order by id
limit 1000000, 20;
```

数据库需要扫描并丢弃大量数据。

优化方案：

### 1. 游标翻页

```sql
select * from order_info
where id > ?
order by id
limit 20;
```

### 2. 延迟关联

```sql
select o.*
from order_info o
join (
    select id
    from order_info
    order by id
    limit 1000000, 20
) t on o.id = t.id;
```

### 3. 限制深分页

产品层面限制只能查看前 N 页。

### 4. 使用搜索引擎

复杂检索和排序交给 ES 等系统。

## 八、锁等待导致慢 SQL

慢不一定是执行计划差，也可能是等锁。

排查：

```sql
show processlist;
show engine innodb status;
```

关注：

- Waiting for lock。
- 事务是否长时间未提交。
- 是否有大事务。
- 是否有间隙锁冲突。

## 九、大事务问题

大事务风险：

- 持锁时间长。
- undo log 膨胀。
- 主从延迟。
- 回滚成本高。
- 影响 purge。

优化：

- 拆批提交。
- 控制单事务数据量。
- 避免事务内远程调用。

## 十、生产经验

- 索引不是越多越好，写入也要维护索引。
- 建索引前看查询频率和选择性。
- Explain 是预估，不等于真实执行耗时。
- 慢 SQL 要结合具体参数分析。
- SQL 优化后要压测验证，避免优化 A 场景恶化 B 场景。
- 大表 DDL 要谨慎，评估锁表和回滚方案。

## 十一、面试表达模板

```text
我会先从慢查询日志定位 SQL，再用 Explain 看 type、key、rows 和 Extra。
如果是 ALL 或 rows 很大，优先看索引是否命中、联合索引顺序是否匹配。
如果出现 Using filesort 或 Using temporary，会看 order by/group by 是否能利用索引。
如果执行计划没问题但仍然慢，会排查锁等待、大事务、IO 和返回数据量。
优化后一定要结合真实参数和压测验证。
```

