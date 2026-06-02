它们都属于 Spring AOP 里的概念，可以按“从声明到执行”的链路理解：

```text
切面 Aspect
  = 切点 Pointcut + 通知 Advice

Advisor
  = Pointcut + Advice 的 Spring 内部表示

Interceptor
  = Advice 在运行时真正执行的一种拦截器形态
```

更具体一点：

| 概念          | 英文                  | 含义                                            |
| ----------- | ------------------- | --------------------------------------------- |
| 切面          | `Aspect`            | 一组横切逻辑的模块化封装                                  |
| 切点          | `Pointcut`          | 定义哪些方法需要被增强                                   |
| 通知          | `Advice`            | 定义什么时候、做什么增强                                  |
| Advisor     | `Advisor`           | Spring AOP 内部使用的对象，通常包含 `Pointcut` + `Advice` |
| Interceptor | `MethodInterceptor` | 方法调用时真正参与拦截执行的对象                              |

举个例子：

```java
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.demo.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void before() {
        System.out.println("before method");
    }
}
```

这里：

```text
LogAspect        -> 切面 Aspect
serviceMethods() -> 切点 Pointcut
@Before          -> 通知 Advice
```

Spring 启动时不会直接拿 `@Aspect` 去执行，而是会解析成更底层的结构：

```text
@Aspect 类
  -> 解析出 Pointcut
  -> 解析出 Advice
  -> 组合成 Advisor
```

运行时调用方法时，大概是：

```text
代理对象
  -> Advisor 匹配当前方法
  -> 将 Advice 适配成 MethodInterceptor
  -> 组成拦截器链
  -> 执行业务方法
```

可以这么理解它们的层次：

```text
开发者视角：
Aspect = Pointcut + Advice

Spring 内部视角：
Advisor = Pointcut + Advice

运行时执行视角：
Interceptor Chain = 多个 MethodInterceptor
```

通知类型和拦截器关系也很关键。

常见通知：

```java
@Before
@After
@AfterReturning
@AfterThrowing
@Around
```

这些注解最终都会被 Spring 适配成拦截器链中的 `MethodInterceptor`。

例如：

```text
@Before advice
  -> MethodBeforeAdvice
  -> MethodBeforeAdviceInterceptor

@AfterReturning advice
  -> AfterReturningAdvice
  -> AfterReturningAdviceInterceptor

@Around advice
  -> MethodInterceptor
```

其中 `@Around` 最接近底层拦截器模型，因为它可以手动控制是否继续执行目标方法：

```java
@Around("serviceMethods()")
public Object around(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("before");
    Object result = pjp.proceed();
    System.out.println("after");
    return result;
}
```

它类似：

```java
public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("before");
    Object result = invocation.proceed();
    System.out.println("after");
    return result;
}
```

一句话总结：

`Aspect` 是开发者写的切面类，`Pointcut` 决定拦谁，`Advice` 决定怎么增强；Spring 会把它们解析成 `Advisor`，方法真正执行时再把匹配到的 `Advisor` 转成一条 `Interceptor` 拦截器链。