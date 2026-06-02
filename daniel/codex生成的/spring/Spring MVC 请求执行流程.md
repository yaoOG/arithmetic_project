# Spring MVC 请求执行流程

## 一、先说结论

Spring MVC 的核心是 `DispatcherServlet`，它是前端控制器，负责把一次 HTTP 请求分发到具体 Controller 方法。

核心链路：

```text
客户端请求
-> Filter
-> DispatcherServlet
-> HandlerMapping 找 Handler
-> HandlerAdapter 调用 Handler
-> 参数解析
-> Controller 方法执行
-> 返回值处理
-> 异常处理
-> 视图渲染或 JSON 写出
-> Interceptor afterCompletion
-> Filter 返回
```

资深开发需要重点掌握：

- Filter 和 Interceptor 的边界。
- HandlerMapping 和 HandlerAdapter 的分工。
- 参数解析器和返回值处理器。
- 全局异常处理。

## 二、DispatcherServlet 的作用

`DispatcherServlet` 是 Spring MVC 的请求总入口。

它不直接写业务逻辑，而是协调各个组件完成请求处理：

- 找到该请求对应哪个 Controller 方法。
- 执行拦截器。
- 解析方法参数。
- 调用 Controller。
- 处理返回值。
- 处理异常。
- 渲染响应。

## 三、核心组件

| 组件 | 作用 |
|---|---|
| HandlerMapping | 根据请求找到 Handler |
| HandlerAdapter | 适配并调用 Handler |
| HandlerInterceptor | 请求拦截 |
| HandlerMethodArgumentResolver | 参数解析 |
| HandlerMethodReturnValueHandler | 返回值处理 |
| HttpMessageConverter | HTTP body 和 Java 对象互转 |
| ViewResolver | 解析视图 |
| HandlerExceptionResolver | 异常处理 |

## 四、完整请求流程

### 1. 请求先经过 Filter

Filter 属于 Servlet 规范，不是 Spring MVC 独有。

常见 Filter：

- 字符编码。
- CORS。
- Spring Security。
- 链路追踪 traceId。
- 请求日志。

### 2. 进入 DispatcherServlet

所有匹配的请求进入 `DispatcherServlet#doDispatch`。

### 3. HandlerMapping 查找 Handler

HandlerMapping 根据 URL、HTTP 方法、请求头等条件找到对应处理器。

典型处理器是：

```text
HandlerMethod，也就是某个 Controller 的某个方法。
```

### 4. 执行 Interceptor preHandle

如果配置了拦截器，会先执行：

```java
preHandle(request, response, handler)
```

如果返回 false，请求不会继续进入 Controller。

### 5. HandlerAdapter 调用 Controller

Spring MVC 不直接调用 Handler，而是通过 HandlerAdapter 适配调用。

对于 `@RequestMapping` 方法，常见适配器是：

```text
RequestMappingHandlerAdapter
```

### 6. 参数解析

Controller 方法参数由 `HandlerMethodArgumentResolver` 解析。

常见注解：

- `@RequestParam`
- `@PathVariable`
- `@RequestBody`
- `@RequestHeader`
- `@CookieValue`
- `@ModelAttribute`

其中 `@RequestBody` 通常依赖 `HttpMessageConverter` 把 JSON 转成 Java 对象。

### 7. 执行业务方法

调用 Controller 方法。

注意：Controller 也可能是代理对象，如果上面有 AOP 增强，会进入代理逻辑。

### 8. 返回值处理

返回值由 `HandlerMethodReturnValueHandler` 处理。

常见返回类型：

- `String`
- `ModelAndView`
- `ResponseEntity`
- 普通 Java 对象 + `@ResponseBody`
- `void`

如果是 `@ResponseBody` 或 `@RestController`，通常通过 `HttpMessageConverter` 写 JSON 响应。

### 9. Interceptor postHandle

Controller 正常执行后，视图渲染前调用：

```java
postHandle(request, response, handler, modelAndView)
```

REST API 返回 JSON 时，这一步不一定有传统视图意义。

### 10. 异常处理

如果处理过程中抛异常，会进入异常解析器：

- `@ExceptionHandler`
- `@ControllerAdvice`
- `ResponseStatusException`
- 默认异常处理器

生产中建议统一异常响应格式，避免 Controller 到处 try-catch。

### 11. afterCompletion

请求完成后执行：

```java
afterCompletion(request, response, handler, exception)
```

适合清理资源、记录最终日志。

## 五、Filter 和 Interceptor 区别

| 对比点 | Filter | Interceptor |
|---|---|---|
| 规范来源 | Servlet 规范 | Spring MVC |
| 执行位置 | DispatcherServlet 之前/之后 | DispatcherServlet 内部 |
| 能否拿到 Handler | 不直接知道 | 可以拿到 Handler |
| 作用范围 | 更底层，所有 Servlet 请求 | Spring MVC 请求 |
| 常见用途 | 安全、编码、跨域、日志 | 登录校验、权限、业务拦截 |

一句话：

```text
Filter 更靠近 Web 容器，Interceptor 更靠近 Spring MVC 业务处理链路。
```

## 六、@RequestBody 和 @ResponseBody 原理

### @RequestBody

```text
HTTP body JSON
-> HttpMessageConverter
-> Java 对象
-> Controller 参数
```

常见转换器：

```text
MappingJackson2HttpMessageConverter
```

### @ResponseBody

```text
Controller 返回 Java 对象
-> HttpMessageConverter
-> JSON
-> HTTP response body
```

## 七、常见生产问题

### 1. 请求进不了 Controller

排查：

- URL 和 method 是否匹配。
- Filter 是否提前拦截。
- Interceptor `preHandle` 是否返回 false。
- Spring Security 是否拦截。
- 参数解析是否失败。

### 2. JSON 反序列化失败

排查：

- Content-Type 是否是 `application/json`。
- 字段类型是否匹配。
- 时间格式是否配置。
- 枚举值是否兼容。
- 是否缺少无参构造器或 setter。

### 3. 全局异常没生效

排查：

- `@ControllerAdvice` 是否被扫描。
- 异常类型是否匹配。
- 是否在 Filter 层就抛出异常。
- 是否被其他异常处理器提前处理。

## 八、参考

- Spring Framework Reference: Web MVC  
  https://docs.spring.io/spring-framework/reference/web/webmvc.html

