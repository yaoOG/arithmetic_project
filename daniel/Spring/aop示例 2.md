面试官您好！咱们前面通过“洋葱模型”聊了 AOP 的底层执行链路，现在我就给您手写一个在实际生产环境中最常用、最优雅的 AOP 代码骨架。

在现代 Spring Boot 开发中，我们已经极少使用基于完整包路径的切入点表达式（比如 `execution(* com.example..*.*(..))`，这种写法容易“误伤”无辜方法，而且重构时极易失效）。

**老兵强烈推荐的实战做法是：“自定义注解 + AOP 环绕通知”。** 我们指哪打哪，极其精准。

下面是一个完整的**“记录接口耗时与异常日志”**的实战代码示例：

### 1. 引入依赖 (Maven)

首先，确保你的 Spring Boot 项目中引入了 AOP 启动器：

XML

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 2. 第一步：定义一个“门牌号”（自定义注解）

这个注解就像是一个标记，只要贴上这个标记的方法，就会被我们的 AOP “保安”拦截。

Java

```
import java.lang.annotation.*;

/**
 * 自定义注解：用于标记需要记录执行时间的业务方法
 */
@Target(ElementType.METHOD) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效，这样 AOP 才能反射拿到
@Documented
public @interface TrackExecutionTime {
    // 可以定义一些属性，比如操作名称
    String operationName() default "未知操作";
}
```

### 3. 第二步：编写纯粹的业务逻辑（目标类 Target）

在业务代码中，我们只需要关注核心逻辑，不需要写任何 `try-catch` 日志或耗时计算逻辑，只需要贴上我们刚定义的注解即可。

Java

```
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    // 贴上注解，告诉 AOP 这里需要被代理
    @TrackExecutionTime(operationName = "创建订单核心逻辑")
    @Override
    public String createOrder(String productId, int quantity) {
        // 纯粹的业务逻辑
        System.out.println(">>> 正在执行创建订单业务...");
        
        // 模拟一点耗时
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // 模拟可能发生的异常
        if (quantity < 1) {
            throw new IllegalArgumentException("订单数量不能小于1");
        }

        return "ORDER_SUCCESS_20260414";
    }
}
```

### 4. 第三步：编写切面类（保安室 Aspect）

这是 AOP 的核心灵魂所在。我们使用 `@Around`（环绕通知），它能完美包裹住上面的 `createOrder` 方法。

Java

```
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Aspect      // 告诉 Spring 这是一个切面类（保安室）
@Component   // 必须交给 IoC 容器管理
public class ExecutionTimeAspect {

    /**
     * 定义环绕通知：拦截所有标有 @TrackExecutionTime 注解的方法
     * @param joinPoint 包含了当前被拦截方法的所有上下文信息
     */
    @Around("@annotation(com.example.annotation.TrackExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        
        long startTime = System.currentTimeMillis();
        
        // 1. 获取方法签名和注解上的属性 (进门前搜身)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TrackExecutionTime annotation = method.getAnnotation(TrackExecutionTime.class);
        String opName = annotation.operationName();
        String methodName = signature.getDeclaringTypeName() + "." + method.getName();

        System.out.println("[AOP-前置] 开始执行: [" + opName + "], 方法: " + methodName);

        Object result = null;
        try {
            // =========================================================
            // 2. 极其重要：放行！调用真实的业务方法 (Target.createOrder)
            // =========================================================
            result = joinPoint.proceed();
            
            // 3. 正常返回后的逻辑 (@AfterReturning)
            System.out.println("[AOP-返回] 业务执行成功，返回值: " + result);
            
        } catch (Throwable ex) {
            // 4. 发生异常时的逻辑 (@AfterThrowing)
            System.err.println("[AOP-异常] 业务执行报错: " + ex.getMessage());
            // 处理完一定要把异常抛出去，否则全局异常处理器或者事务管理器捕获不到
            throw ex; 
        } finally {
            // 5. 最终一定会执行的逻辑 (@After)
            long executionTime = System.currentTimeMillis() - startTime;
            System.out.println("[AOP-最终] [" + opName + "] 执行结束，总耗时: " + executionTime + " ms");
            System.out.println("--------------------------------------------------");
        }

        return result;
    }
}
```

### 💡 老兵代码复盘

在这段代码中，您可以清晰地看到 AOP 的三大优势：

1. **解耦**：`OrderServiceImpl` 里干干净净，只有业务代码，完全不知道日志和耗时监控的存在。
    
2. **复用**：如果明天要给 `PaymentService` 的支付接口也加上耗时监控，一行代码不用重写，只要在目标方法上加个 `@TrackExecutionTime` 即可。
    
3. **掌控力**：`joinPoint.proceed()` 是最强大的武器。如果你在这里加上权限校验逻辑，一旦发现权限不足，你可以**直接 `return` 错误信息或者抛出异常，根本不调用 `proceed()`**，这样业务代码连执行的机会都没有，完美实现了安全拦截。