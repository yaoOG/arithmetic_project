# Spring Boot 自动配置原理

## 一、先说结论

Spring Boot 自动配置的核心思想是：

```text
根据 classpath 中的依赖、已有 Bean、配置属性和运行环境，自动装配一组默认 Bean。
```

它不是“无脑创建 Bean”，而是大量依赖条件注解：

- `@ConditionalOnClass`
- `@ConditionalOnMissingBean`
- `@ConditionalOnProperty`
- `@ConditionalOnBean`
- `@ConditionalOnWebApplication`

一句话：

```text
Spring Boot 自动配置是约定优于配置，但用户自定义 Bean 的优先级通常高于默认自动配置。
```

## 二、@SpringBootApplication 包含什么

`@SpringBootApplication` 是组合注解，核心包括：

```text
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

### 1. @SpringBootConfiguration

本质上是 `@Configuration`，说明当前类是配置类。

### 2. @ComponentScan

默认从启动类所在包开始扫描组件。

生产建议：

```text
启动类尽量放在项目根包下，否则可能导致组件扫描不完整。
```

### 3. @EnableAutoConfiguration

开启自动配置，是 Spring Boot 的关键入口。

## 三、自动配置加载流程

主流程可以简化为：

```text
启动 SpringApplication
-> 创建 ApplicationContext
-> 解析 @SpringBootApplication
-> 处理 @EnableAutoConfiguration
-> 加载候选自动配置类
-> 根据 @Conditional 条件过滤
-> 注册满足条件的 BeanDefinition
-> 创建 Bean
```

## 四、Spring Boot 2 和 Spring Boot 3 的差异

Spring Boot 2 早期常见自动配置入口：

```text
META-INF/spring.factories
```

Spring Boot 3 主流自动配置入口：

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

自定义 starter 时，要按目标 Boot 版本选择正确方式。面向 Spring Boot 3，优先使用 `AutoConfiguration.imports`。

## 五、条件装配注解

### 1. @ConditionalOnClass

classpath 中存在某个类时生效。

示例：

```text
项目引入 Redis 依赖后，Redis 相关自动配置才有机会生效。
```

### 2. @ConditionalOnMissingBean

容器中没有某个 Bean 时才创建默认 Bean。

这是 Spring Boot “不抢用户配置”的关键。

面试表达：

```text
如果业务自己定义了 DataSource，自动配置里的默认 DataSource 就会退让。
```

### 3. @ConditionalOnProperty

根据配置项决定是否启用。

常用于开关类能力：

```text
xxx.enabled=true
```

### 4. @ConditionalOnBean

容器中存在某个 Bean 时才生效。

### 5. @ConditionalOnWebApplication

当前是 Web 应用时才生效。

## 六、为什么自动配置不会覆盖用户配置

关键在于大量默认 Bean 使用了：

```java
@ConditionalOnMissingBean
```

它的含义是：

```text
如果容器中已经有用户自己定义的同类型 Bean，自动配置就不再创建默认 Bean。
```

这就是 Spring Boot 自动配置“非侵入式”的核心。

## 七、如何查看自动配置生效情况

### 1. 启动参数

```bash
--debug
```

可以输出 Conditions Evaluation Report，查看哪些自动配置生效、哪些没生效以及原因。

### 2. Actuator

引入 actuator 后可通过条件报告端点观察自动配置情况，具体端点是否暴露取决于 actuator 配置。

## 八、如何自定义 starter

一个标准 starter 通常拆成两部分：

```text
xxx-spring-boot-autoconfigure：自动配置代码
xxx-spring-boot-starter：依赖聚合
```

核心步骤：

1. 定义配置属性类。
2. 编写自动配置类。
3. 使用条件注解控制装配。
4. 在 `AutoConfiguration.imports` 中声明自动配置类。
5. starter 模块引入 autoconfigure 和必要依赖。

示例：

```java
@AutoConfiguration
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnClass(SmsClient.class)
@ConditionalOnProperty(prefix = "sms", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SmsClient smsClient(SmsProperties properties) {
        return new SmsClient(properties.getAccessKey(), properties.getSecretKey());
    }
}
```

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`：

```text
com.example.sms.SmsAutoConfiguration
```

## 九、常见面试追问

### 1. Spring Boot 为什么能做到开箱即用

因为它通过自动配置类预置了大量默认 Bean，并通过条件注解根据依赖和环境按需生效。

### 2. 自动配置和普通配置类有什么区别

自动配置类本质也是配置类，但它由 Spring Boot 自动发现和加载，并且通常有大量条件注解，避免无条件生效。

### 3. 如果自动配置不符合需求怎么办

- 定义自己的 Bean 覆盖默认配置。
- 使用配置属性调整。
- exclude 指定自动配置类。
- 自定义 starter。

## 十、参考

- Spring Boot Reference: Auto-configuration  
  https://docs.spring.io/spring-boot/reference/using/auto-configuration.html
- Spring Boot Reference: Creating Your Own Auto-configuration  
  https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html

