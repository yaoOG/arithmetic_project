
这是一个在生产环境中踩坑率极高，同时也是高级 Java 面试必问的“试金石”问题。

我们在上一个问题中反复强调了：**Spring AOP 的底层原理是动态代理。**

要彻底理解 AOP 为什么会失效，只需要在脑子里牢记一个核心定律：**“只有当你的方法调用经过了那个戴着面具的‘代理对象（Proxy）’时，AOP 才会生效；如果直接调用了‘真实对象（Target）’，AOP 必然失效。”**

基于这个铁律，生产环境中最常见的 AOP 失效场景可以归结为以下 4 种：

### 1. 内部方法自调用（头号杀手，90% 的人踩过）

这是最容易让人崩溃的场景。假设我们在一个类里写了两个方法：

Java

```
@Service
public class OrderServiceImpl implements OrderService {

    public void createOrder() {
        System.out.println("执行常规订单逻辑...");
        // 直接调用同类中的另一个方法
        this.payOrder(); 
    }

    @Transactional // 或者我们自定义的 @TrackExecutionTime
    public void payOrder() {
        System.out.println("执行支付逻辑，需要事务或AOP切面...");
    }
}
```

- **现象**：当外部 Controller 直接调用 `payOrder()` 时，AOP 正常生效。但是，当外部调用 `createOrder()`，然后 `createOrder()` 内部调用 `payOrder()` 时，`payOrder()` 上的 AOP 或事务**完全失效**！
    
- **老兵剖析**：当调用 `createOrder()` 时，确实进过了代理类。但进入真实对象内部后，执行 `this.payOrder()` 时，这个 `this` 指向的是**真实对象本身**，而不是外面的代理对象。保安（AOP）在大门口，你已经在楼里串门了，保安根本管不着你。
    
- **破解之法**：
    
    1. 极其简单的野路子：在类里面自己注入自己（Spring 支持解决这种循环依赖）。
        
    2. 官方推荐做法：通过 `AopContext.currentProxy()` 获取当前的代理对象，然后用代理对象去调用 `((OrderService) AopContext.currentProxy()).payOrder()`。（需在启动类加上 `@EnableAspectJAutoProxy(exposeProxy = true)`）。
        

### 2. 方法修饰符非 `public`

- **现象**：如果你把加了 AOP 注解的方法改成了 `private`、`protected` 或者包可见（默认修饰符）。
    
- **老兵剖析**：不管是 JDK 动态代理（基于接口，接口方法天然是 public 的）还是 CGLIB 代理（基于继承生成子类），Spring 的底层源码（`AbstractFallbackTransactionAttributeSource` 等）默认都会去校验方法的修饰符。**如果不是 public，Spring 会直接忽略这个方法，不会为它织入切面逻辑。**
    

### 3. 方法被 `final` 或 `static` 修饰

- **现象**：在使用了 CGLIB 动态代理的场景下（比如类没有实现接口），被 `final` 或 `static` 修饰的方法上的 AOP 会失效。
    
- **老兵剖析**：
    
    - **`final`**：CGLIB 的原理是动态生成一个目标类的**子类**，并重写（Override）父类的方法来加入增强逻辑。Java 语法规定，`final` 方法绝对不能被重写。所以 CGLIB 对 `final` 方法束手无策。
        
    - **`static`**：静态方法属于“类”本身，而不属于“实例对象”。动态代理拦截的是对象实例的方法调用，因此静态方法无法被 AOP 拦截。
        

### 4. 目标类根本没有交给 Spring 容器管理

- **现象**：在某些复杂的旧项目重构中，开发人员手动 `new` 了一个对象：`OrderService service = new OrderServiceImpl();`，然后抱怨上面的 AOP 没生效。
    
- **老兵剖析**：IoC 和 AOP 是强绑定的。AOP 的代理对象是在 IoC 容器的 Bean 生命周期（BeanPostProcessor 阶段）中被掉包生成的。你如果自己 `new` 对象，直接跳过了整个 Spring 容器的生命周期，生出来的就是一个纯粹的、没有任何魔法的普通 Java 对象。
    

---
