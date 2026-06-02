# BeanFactory 和 ApplicationContext 区别

## 一、先说结论

`BeanFactory` 是 Spring IoC 容器的基础接口，提供最基本的 Bean 创建、获取和依赖注入能力。

`ApplicationContext` 是 `BeanFactory` 的高级子接口，面向企业级应用，额外提供事件、国际化、资源加载、环境抽象、自动注册后置处理器等能力。

一句话：

```text
BeanFactory 是基础容器，ApplicationContext 是完整应用上下文。
```

实际开发中几乎都使用 `ApplicationContext`。

## 二、核心区别

| 对比点 | BeanFactory | ApplicationContext |
|---|---|---|
| 定位 | 基础 IoC 容器 | 企业级应用上下文 |
| Bean 创建 | 默认懒加载 | 默认启动时预实例化非懒加载单例 |
| 后置处理器 | 需要手动注册 | 自动识别并注册 |
| 国际化 | 不直接提供 | 支持 MessageSource |
| 事件机制 | 不直接提供完整能力 | 支持 ApplicationEvent |
| 资源加载 | 基础能力较弱 | 支持 ResourcePatternResolver |
| 环境抽象 | 不完整 | 支持 Environment、Profile、PropertySource |
| Web 集成 | 不直接面向 Web | 有 WebApplicationContext |

## 三、BeanFactory 的职责

`BeanFactory` 最核心的能力是：

- 根据 beanName 获取 Bean。
- 判断 Bean 是否存在。
- 判断 Bean 类型。
- 管理 Bean 的依赖关系。
- 支持 singleton、prototype 等作用域。

典型方法：

```java
Object getBean(String name);
<T> T getBean(Class<T> requiredType);
boolean containsBean(String name);
boolean isSingleton(String name);
Class<?> getType(String name);
```

面试表达：

```text
BeanFactory 是 Spring IoC 最底层的容器抽象，ApplicationContext 的很多能力最终也建立在 BeanFactory 之上。
```

## 四、ApplicationContext 额外提供什么

### 1. 自动注册 BeanPostProcessor

`ApplicationContext` 会自动发现并注册容器中的 `BeanPostProcessor`。

这非常关键，因为 Spring 的很多能力依赖后置处理器：

- `@Autowired`
- `@PostConstruct`
- AOP
- `@Async`
- `@Transactional`

如果只使用底层 `BeanFactory`，很多处理器需要手动注册，否则注解能力可能不生效。

### 2. 国际化 MessageSource

支持根据 Locale 获取不同语言文案。

典型场景：

- 错误码文案。
- 多语言站点。
- 国际化提示。

### 3. 事件发布

ApplicationContext 支持事件机制：

```java
applicationContext.publishEvent(new OrderCreatedEvent(orderId));
```

监听方式：

```java
@EventListener
public void onOrderCreated(OrderCreatedEvent event) {
    // ...
}
```

适合做应用内解耦，但不适合作为跨服务可靠消息。

### 4. 资源加载

可以统一加载：

- classpath 资源。
- 文件系统资源。
- URL 资源。
- Ant 风格路径资源。

### 5. Environment 和 Profile

支持：

- 读取配置属性。
- 区分 dev/test/prod 环境。
- 条件化注册 Bean。

例如：

```java
@Profile("prod")
@Bean
public DataSource prodDataSource() {
    return dataSource;
}
```

## 五、为什么 ApplicationContext 默认预实例化单例

ApplicationContext 默认会在容器启动阶段创建非懒加载的 singleton Bean。

好处：

- 启动时尽早发现配置错误。
- 避免运行时第一次访问才暴露问题。
- 单例 Bean 提前完成依赖注入和初始化。

代价：

- 启动时间更长。
- 初始化逻辑过重会拖慢应用启动。

生产建议：

```text
核心 Bean 默认预加载是好事，能让服务失败快。
但大对象、冷门组件、昂贵连接可以结合懒加载或异步预热设计。
```

## 六、常见 ApplicationContext 实现

| 实现类 | 场景 |
|---|---|
| AnnotationConfigApplicationContext | 注解配置的普通应用 |
| ClassPathXmlApplicationContext | XML 配置应用 |
| FileSystemXmlApplicationContext | 文件系统 XML |
| AnnotationConfigServletWebServerApplicationContext | Spring Boot Servlet Web 应用 |
| AnnotationConfigReactiveWebServerApplicationContext | Spring Boot Reactive Web 应用 |

## 七、和 FactoryBean 的区别

容易混淆：

- `BeanFactory`：容器接口。
- `FactoryBean`：一个特殊 Bean，用来自定义对象创建。
- `ApplicationContext`：BeanFactory 的高级应用上下文。

一句话：

```text
BeanFactory 是工厂，FactoryBean 是工厂里的一个特殊 Bean。
```

## 八、常见面试追问

### 1. 为什么日常开发不用 BeanFactory

因为业务应用需要的不只是获取 Bean，还需要 AOP、事务、事件、配置、国际化、资源加载、Web 集成等能力，ApplicationContext 更完整。

### 2. BeanFactory 是否支持懒加载

BeanFactory 默认按需创建 Bean。ApplicationContext 默认启动时预实例化非懒加载 singleton。

### 3. ApplicationContext 和 Spring Boot 有什么关系

Spring Boot 启动时会根据应用类型创建不同的 ApplicationContext，然后在 refresh 流程中完成 BeanFactory 准备、BeanDefinition 加载、Bean 创建、Web 容器启动等工作。

## 九、参考

- Spring Framework Reference: BeanFactory API  
  https://docs.spring.io/spring-framework/reference/core/beans/beanfactory.html
- Spring Framework Reference: Additional Capabilities of ApplicationContext  
  https://docs.spring.io/spring-framework/reference/core/beans/context-introduction.html

