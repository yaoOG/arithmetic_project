# Spring Boot 启动流程

## 一、先说结论

Spring Boot 启动不是只有一句 `SpringApplication.run()`，它背后做了四类事情：

```text
准备运行环境
-> 创建 ApplicationContext
-> 加载 BeanDefinition
-> refresh 容器
-> 启动 Web 容器和应用回调
```

如果从源码主线看，核心就是：

```java
SpringApplication.run(Application.class, args);
```

内部围绕 `SpringApplication` 和 `ApplicationContext#refresh()` 展开。

## 二、启动主流程

简化流程：

```text
1. 创建 SpringApplication 对象。
2. 推断应用类型：Servlet、Reactive、None。
3. 加载 ApplicationContextInitializer。
4. 加载 ApplicationListener。
5. 推断主启动类。
6. 执行 run 方法。
7. 准备 Environment。
8. 创建 ApplicationContext。
9. 准备 Context。
10. 加载 BeanDefinition。
11. refresh 容器。
12. 执行 CommandLineRunner / ApplicationRunner。
13. 发布启动完成事件。
```

## 三、创建 SpringApplication

构造 `SpringApplication` 时会做一些基础推断：

### 1. 推断 Web 应用类型

常见类型：

- `SERVLET`
- `REACTIVE`
- `NONE`

如果 classpath 中有 Spring MVC 相关类，通常是 Servlet Web 应用。

### 2. 加载初始化器和监听器

Spring Boot 会从配置中加载：

- `ApplicationContextInitializer`
- `ApplicationListener`

这些扩展点可以参与启动过程。

## 四、准备 Environment

Environment 负责管理配置来源，包括：

- 命令行参数。
- 系统环境变量。
- JVM 系统属性。
- application.yml。
- application.properties。
- profile 配置。
- 配置中心加载的属性。

这一阶段会确定：

- active profile。
- 配置属性。
- 日志相关早期配置。

生产中常见问题：

```text
配置没生效，优先排查 profile、配置优先级、配置文件位置、环境变量覆盖。
```

## 五、创建 ApplicationContext

Spring Boot 会根据应用类型创建不同上下文。

例如 Servlet Web 应用常见：

```text
AnnotationConfigServletWebServerApplicationContext
```

它不仅是 Spring 容器，还负责内嵌 WebServer 的创建和启动。

## 六、准备 ApplicationContext

准备阶段会做：

- 设置 Environment。
- 应用 ApplicationContextInitializer。
- 注册一些基础 Bean。
- 加载启动参数。
- 发布上下文准备事件。

## 七、加载 BeanDefinition

启动类通常有：

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

`@SpringBootApplication` 会触发：

- 组件扫描。
- 配置类解析。
- 自动配置导入。

最终把 Bean 注册为 `BeanDefinition`。

注意：

```text
这一步主要是注册 BeanDefinition，不等于所有 Bean 都已经实例化。
```

## 八、refresh 容器核心流程

`refresh()` 是 Spring 容器启动最核心的方法。

简化流程：

```text
1. prepareRefresh：准备刷新上下文。
2. obtainFreshBeanFactory：获取 BeanFactory。
3. prepareBeanFactory：准备 BeanFactory，注册基础组件。
4. postProcessBeanFactory：子类扩展点。
5. invokeBeanFactoryPostProcessors：执行 BeanFactoryPostProcessor。
6. registerBeanPostProcessors：注册 BeanPostProcessor。
7. initMessageSource：初始化国际化。
8. initApplicationEventMulticaster：初始化事件广播器。
9. onRefresh：子类扩展，Web 容器创建常在这里发生。
10. registerListeners：注册监听器。
11. finishBeanFactoryInitialization：实例化非懒加载单例 Bean。
12. finishRefresh：发布刷新完成事件。
```

面试重点：

```text
BeanFactoryPostProcessor 在 Bean 创建前修改 BeanDefinition。
BeanPostProcessor 在 Bean 创建过程中增强 Bean。
非懒加载单例 Bean 通常在 finishBeanFactoryInitialization 阶段创建。
```

## 九、Web 容器什么时候启动

Spring Boot 内嵌 Tomcat/Jetty/Undertow 的启动和 WebServerApplicationContext 有关。

对于 Servlet Web 应用，内嵌 Web 容器通常在 refresh 的 `onRefresh` 附近被创建，并在后续阶段完成启动。

这也是为什么 Spring Boot 可以直接运行 jar：

```text
它不是部署到外部 Tomcat，而是在应用内部创建并启动了 Web 服务器。
```

## 十、Runner 回调

容器启动后，会执行：

- `ApplicationRunner`
- `CommandLineRunner`

适合做启动后的轻量任务：

- 打印启动信息。
- 检查关键配置。
- 预热本地缓存。

注意：

```text
Runner 不要执行无限循环或长时间阻塞任务，否则会影响应用启动完成。
```

## 十一、常见扩展点

| 扩展点 | 执行阶段 | 典型用途 |
|---|---|---|
| ApplicationContextInitializer | Context refresh 前 | 修改上下文、注册属性 |
| ApplicationListener | 启动事件阶段 | 监听启动过程 |
| EnvironmentPostProcessor | Environment 准备阶段 | 加载自定义配置 |
| BeanFactoryPostProcessor | Bean 创建前 | 修改 BeanDefinition |
| BeanPostProcessor | Bean 创建中 | 注入、代理、增强 |
| ApplicationRunner | 容器启动后 | 启动后任务 |
| CommandLineRunner | 容器启动后 | 命令行任务 |

## 十二、常见面试追问

### 1. Bean 是什么时候创建的

非懒加载 singleton Bean 通常在 `refresh()` 的 `finishBeanFactoryInitialization` 阶段创建。

### 2. 自动配置什么时候加载

自动配置在配置类解析和 BeanDefinition 加载阶段被导入，满足条件的自动配置类会注册相应 BeanDefinition。

### 3. Spring Boot 怎么启动内嵌 Tomcat

Spring Boot 根据 Web 应用类型创建 WebServerApplicationContext，在 refresh 过程中创建并启动内嵌 WebServer。

### 4. 配置文件什么时候读取

Environment 准备阶段会加载配置属性，并在后续 Bean 创建、条件装配、属性绑定中使用。

## 十三、生产经验

- 启动慢要看 Bean 初始化耗时、远程连接、Runner、自动配置扫描范围。
- 配置不生效优先看 profile 和配置优先级。
- 自定义 starter 不要在自动配置类里做重初始化，应该创建 Bean，让生命周期交给容器。
- 启动后预热要可降级，避免预热失败导致服务无法启动。
- Bean 创建阶段不要依赖尚未完成初始化的代理能力。

## 十四、参考

- Spring Boot Reference: Using Spring Boot  
  https://docs.spring.io/spring-boot/reference/using/index.html
- Spring Boot Reference: Auto-configuration  
  https://docs.spring.io/spring-boot/reference/using/auto-configuration.html

