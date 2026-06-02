# Spring AOP 原理

## 一、先说结论

Spring AOP 的本质是代理模式：

```text
调用方 -> 代理对象 -> 拦截器链 -> 目标对象方法
```

Spring AOP 默认不是直接修改目标类字节码，而是在 Bean 生命周期后期创建代理对象，并把代理对象放入容器。

核心点：

- 有接口时默认倾向使用 JDK 动态代理。
- 没有接口时使用 CGLIB 创建子类代理。
- Spring 事务、缓存、异步、权限校验、日志切面等都依赖 AOP 代理。
- 同类方法内部调用会绕过代理，导致 AOP 失效。

## 二、AOP 关键概念

| 概念 | 含义 |
|---|---|
| Join Point | 可被增强的连接点，Spring AOP 中主要是方法执行 |
| Pointcut | 切点，决定哪些方法需要增强 |
| Advice | 通知，增强逻辑 |
| Aspect | 切面，Pointcut + Advice |
| Advisor | Spring 内部常用模型，Advice + Pointcut |
| Proxy | 代理对象 |
| Target | 目标对象 |
| Interceptor | 拦截器，真正执行增强链路 |

面试表达：

```text
Spring AOP 会把切面解析成 Advisor，再由自动代理创建器判断某个 Bean 是否匹配这些 Advisor。
如果匹配，就为 Bean 创建代理对象，调用方法时走拦截器链。
```

## 三、JDK 动态代理和 CGLIB 区别

| 对比点 | JDK 动态代理 | CGLIB |
|---|---|---|
| 代理方式 | 基于接口 | 基于继承生成子类 |
| 要求 | 目标类必须实现接口 | 目标类不能是 final |
| 方法限制 | 只能代理接口方法 | 不能代理 final/private 方法 |
| 核心机制 | InvocationHandler | MethodInterceptor |
| Spring 使用场景 | 有接口时默认可用 | 无接口或强制 class 代理 |

### 生产注意

- `final` 类无法被 CGLIB 代理。
- `final` 方法无法被 CGLIB 增强。
- `private` 方法不能被 Spring AOP 正常增强。
- 静态方法不属于实例代理范围。

## 四、代理对象创建时机

Spring AOP 代理通常由 `BeanPostProcessor` 完成，典型实现是：

```text
AnnotationAwareAspectJAutoProxyCreator
```

大致流程：

```text
Bean 实例化
-> 属性填充
-> 初始化
-> BeanPostProcessor 后置处理
-> 判断是否有 Advisor 匹配当前 Bean
-> 如果匹配，创建代理对象
-> 代理对象进入容器
```

也就是说：

```text
容器中最终被其他 Bean 注入的通常是代理对象，而不是原始目标对象。
```

## 五、方法调用主链路

当调用一个被 AOP 增强的方法时：

```text
代理对象方法
-> 获取匹配当前方法的拦截器链
-> 按顺序执行前置增强
-> 调用目标对象方法
-> 执行后置增强/返回增强/异常增强
-> 返回结果
```

事务就是典型例子：

```text
代理对象
-> TransactionInterceptor
-> 开启事务
-> 调用目标方法
-> 成功提交 / 异常回滚
```

## 六、为什么同类方法内部调用会导致 AOP 失效

示例：

```java
@Service
public class OrderService {

    public void createOrder() {
        this.deductStock();
    }

    @Transactional
    public void deductStock() {
        // 扣库存
    }
}
```

问题在于：

```text
createOrder 调用 deductStock 使用的是 this，不是 Spring 容器里的代理对象。
```

调用链变成：

```text
外部 -> 代理对象.createOrder()
-> 目标对象.createOrder()
-> this.deductStock()
```

`deductStock()` 没有经过代理对象，因此事务拦截器不会执行。

解决方式：

- 把被增强方法拆到另一个 Spring Bean。
- 通过代理对象调用自身方法，但不推荐滥用。
- 使用 AspectJ 编译期或加载期织入，但复杂度更高。

最佳实践：

```text
如果一个方法需要事务、异步、缓存等 AOP 能力，尽量让它被外部 Bean 通过 Spring 代理对象调用。
```

## 七、Spring AOP 和 AspectJ 的区别

| 对比点 | Spring AOP | AspectJ |
|---|---|---|
| 实现方式 | 运行时代理 | 编译期/加载期/运行期织入 |
| 增强范围 | Spring Bean 的方法执行 | 字段、构造器、方法等更广 |
| 使用复杂度 | 低 | 高 |
| 常见场景 | 事务、日志、权限、缓存 | 更底层或非 Spring 对象增强 |

多数业务系统使用 Spring AOP 就够了。

## 八、常见失效场景

- 同类内部调用。
- 方法不是 public，尤其是事务场景。
- 目标类或方法是 final。
- 对象不是 Spring 容器管理的 Bean。
- 手动 `new` 出来的对象。
- 切点表达式没有匹配到。
- Bean 过早初始化，没有完整经过自动代理创建器。

## 九、生产经验

- 事务、缓存、异步等注解，本质都依赖代理，排查失效时先看有没有走代理。
- 不要在构造方法或 `@PostConstruct` 里调用需要 AOP 增强的方法。
- 不要把 AOP 当业务主流程，核心业务逻辑应该显式可读。
- 切面中不要做过重逻辑，否则会影响所有匹配方法。
- 多个切面叠加时，要明确顺序，必要时使用 `@Order`。

