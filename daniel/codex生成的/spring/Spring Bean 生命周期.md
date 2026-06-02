# Spring Bean 生命周期

## 一、先说结论

Spring Bean 生命周期不是简单的“new 一个对象然后放进容器”，而是一条完整的容器管理流水线：

```text
BeanDefinition 注册
-> 实例化
-> 属性填充
-> Aware 回调
-> BeanPostProcessor 前置处理
-> 初始化回调
-> BeanPostProcessor 后置处理
-> 单例池可用
-> 容器关闭时销毁
```

资深开发需要重点理解三件事：

- Spring 管理 Bean 的核心不是 class，而是 `BeanDefinition`。
- `BeanPostProcessor` 是 Spring 扩展能力的关键入口，AOP、@Autowired、@PostConstruct 都依赖各种后置处理器。
- Spring 生命周期中很多“魔法”发生在初始化前后，尤其是代理对象创建。

## 二、BeanDefinition 是什么

`BeanDefinition` 可以理解为 Bean 的“施工图纸”，里面记录了容器创建 Bean 所需的元数据：

- Bean 的 class。
- scope：singleton、prototype 等。
- 是否懒加载。
- 构造参数。
- 属性依赖。
- init-method、destroy-method。
- 是否 primary。
- 自动注入模式。

Spring 启动时，配置类、XML、组件扫描、Import、自动配置等最终都会被解析成 `BeanDefinition`，注册到 `BeanDefinitionRegistry` 中。

面试表达：

```text
Spring 容器不是扫描到类就立刻创建对象，而是先把类解析成 BeanDefinition。
BeanDefinition 是后续实例化、依赖注入、生命周期回调、AOP 代理创建的基础元数据。
```

## 三、完整生命周期主流程

### 1. BeanDefinition 注册

常见来源：

- `@ComponentScan` 扫描到 `@Component`、`@Service`、`@Repository`、`@Controller`。
- `@Bean` 方法。
- `@Import`。
- Spring Boot 自动配置。
- XML `<bean>`。

这一阶段只是在容器中登记“将来怎么创建 Bean”，通常还没有真正创建业务对象。

### 2. 实例化

实例化是创建原始 Java 对象，类似执行：

```java
Object bean = constructor.newInstance();
```

注意：实例化只代表对象被创建出来了，不代表依赖已经注入完成。

实例化方式包括：

- 构造方法。
- 静态工厂方法。
- 实例工厂方法。
- `FactoryBean`。

### 3. 属性填充

Spring 会处理依赖注入：

- `@Autowired`
- `@Resource`
- `@Value`
- XML property
- 构造器参数

字段注入、setter 注入等都在这一阶段完成。

生产建议：

- 核心业务依赖优先使用构造器注入，能让依赖不可变，也更容易测试。
- 但如果存在循环依赖，构造器注入会直接失败，说明设计上大概率需要拆分职责。

### 4. Aware 回调

如果 Bean 实现了某些 `Aware` 接口，Spring 会把容器上下文信息回调给它：

- `BeanNameAware`
- `BeanFactoryAware`
- `ApplicationContextAware`
- `EnvironmentAware`
- `ResourceLoaderAware`

典型用途：

- 获取当前 Bean 名称。
- 获取容器。
- 读取环境配置。
- 发布事件。

注意：业务代码不应滥用 `ApplicationContextAware` 到处手动取 Bean，否则会让代码强耦合 Spring 容器。

### 5. BeanPostProcessor 前置处理

调用：

```java
postProcessBeforeInitialization(bean, beanName)
```

这一阶段发生在初始化方法之前。常见作用：

- 处理 `@PostConstruct`。
- 处理部分框架注解。
- 对 Bean 做初始化前增强。

### 6. 初始化回调

初始化回调常见有三种：

```text
@PostConstruct
-> InitializingBean.afterPropertiesSet()
-> init-method
```

常见用途：

- 建立连接。
- 加载本地缓存。
- 校验配置。
- 初始化客户端。

生产建议：

- 初始化逻辑不要执行太重的远程调用，否则会拖慢应用启动。
- 必须能失败快，否则应用半启动状态很危险。

### 7. BeanPostProcessor 后置处理

调用：

```java
postProcessAfterInitialization(bean, beanName)
```

这是非常关键的一步，AOP 代理通常在这里创建。

比如一个 Service 被事务、异步、缓存、切面增强后，最终放进单例池的可能不是原始对象，而是代理对象。

面试表达：

```text
AOP 不是修改原始对象的方法字节码，而是在 Bean 生命周期后期由 BeanPostProcessor 创建代理对象。
所以容器里最终暴露出来的 Bean 很可能已经不是原始实例，而是代理实例。
```

### 8. 单例池可用

对于 singleton Bean，完成初始化后会放入单例池，后续从容器获取的都是同一个实例。

对于 prototype Bean，Spring 只负责创建和初始化，不负责完整销毁生命周期。

### 9. 销毁

容器关闭时，singleton Bean 会触发销毁流程：

```text
@PreDestroy
-> DisposableBean.destroy()
-> destroy-method
```

常见用途：

- 关闭线程池。
- 释放连接。
- flush 缓冲数据。
- 注销监听器。

## 四、BeanFactoryPostProcessor 和 BeanPostProcessor 的区别

| 对比点 | BeanFactoryPostProcessor | BeanPostProcessor |
|---|---|---|
| 处理对象 | BeanDefinition | Bean 实例 |
| 执行时机 | Bean 实例化之前 | Bean 实例化之后 |
| 典型用途 | 修改 Bean 定义、解析配置 | 注入、初始化、创建代理 |
| 代表实现 | ConfigurationClassPostProcessor | AutowiredAnnotationBeanPostProcessor、AnnotationAwareAspectJAutoProxyCreator |

一句话：

```text
BeanFactoryPostProcessor 改图纸，BeanPostProcessor 改成品。
```

## 五、FactoryBean 和 BeanFactory 的区别

- `BeanFactory` 是 Spring IoC 容器的基础接口。
- `FactoryBean` 是一种特殊 Bean，用来定制某个 Bean 的创建逻辑。

当一个 Bean 实现 `FactoryBean<T>` 时：

- `getBean("xxx")` 拿到的是 `getObject()` 返回的对象。
- `getBean("&xxx")` 拿到的是 FactoryBean 本身。

典型场景：

- MyBatis 的 Mapper 代理对象。
- 复杂第三方客户端创建。
- 框架需要隐藏对象创建细节。

## 六、常见面试追问

### 1. @PostConstruct、InitializingBean、init-method 顺序是什么

通常顺序是：

```text
@PostConstruct
-> InitializingBean.afterPropertiesSet()
-> init-method
```

### 2. AOP 代理在哪一步创建

通常在 `BeanPostProcessor#postProcessAfterInitialization` 中创建，核心实现是自动代理创建器，例如 `AnnotationAwareAspectJAutoProxyCreator`。

### 3. BeanPostProcessor 为什么强大

因为它能拦截每个 Bean 的创建过程，并在初始化前后进行增强。Spring 的依赖注入、生命周期注解、AOP、异步、事务等能力都大量依赖后置处理器。

## 七、生产经验

- 不要在构造方法里访问尚未注入的依赖。
- 不要在 `@PostConstruct` 中启动不可控的长耗时任务。
- 自定义 `BeanPostProcessor` 要注意执行顺序，必要时实现 `PriorityOrdered` 或 `Ordered`。
- 如果某个 Bean 没有被 AOP 增强，优先排查是否过早实例化，导致它没经过完整后置处理器链。
- prototype Bean 的销毁需要业务自己处理，Spring 不会像 singleton 一样完整托管销毁阶段。

## 八、参考

- Spring Framework Reference: Bean Overview  
  https://docs.spring.io/spring-framework/reference/core/beans/definition.html
- Spring Framework Reference: Lifecycle Callbacks  
  https://docs.spring.io/spring-framework/reference/core/beans/factory-nature.html
- BeanPostProcessor Javadoc  
  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html

