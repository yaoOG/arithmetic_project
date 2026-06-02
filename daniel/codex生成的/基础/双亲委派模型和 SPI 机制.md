# 双亲委派模型和 SPI 机制

## 一、先说结论

双亲委派模型的核心是：

```text
类加载器收到加载请求后，先交给父加载器加载。
父加载器加载不了，子加载器才自己加载。
```

它的价值：

- 避免类重复加载。
- 保护 JDK 核心类库安全。
- 保证 Java 类型体系稳定。

SPI 的特殊点是：

```text
JDK 核心类定义接口，但具体实现类在应用 classpath 中。
所以需要通过线程上下文类加载器，让父层代码反向加载子层实现。
```

## 二、双亲委派流程

简化源码逻辑：

```text
1. 先检查类是否已经加载。
2. 如果没加载，委托父加载器加载。
3. 父加载器加载失败，当前加载器自己加载。
```

伪代码：

```java
protected Class<?> loadClass(String name) {
    Class<?> c = findLoadedClass(name);
    if (c == null) {
        try {
            if (parent != null) {
                c = parent.loadClass(name);
            } else {
                c = findBootstrapClassOrNull(name);
            }
        } catch (ClassNotFoundException e) {
            c = findClass(name);
        }
    }
    return c;
}
```

## 三、为什么需要双亲委派

### 1. 避免重复加载

如果每个类加载器都自己加载 `java.lang.String`，JVM 内会出现多个 String 类型，类型体系会崩。

### 2. 保护核心类库

如果业务自己写一个：

```java
package java.lang;
public class String {}
```

双亲委派会优先由 Bootstrap ClassLoader 加载 JDK 自带 String，避免核心类被篡改。

### 3. 保证类型一致性

同一个核心类由最顶层加载器统一加载，避免不同模块拿到不同版本。

## 四、如何打破双亲委派

常见方式：

- 重写 `loadClass()`，改变委派逻辑。
- 重写 `findClass()`，保留委派但自定义查找。
- 使用线程上下文类加载器。
- 容器类加载器隔离，如 Tomcat。
- OSGi、插件系统。

注意：

```text
一般建议重写 findClass，而不是直接重写 loadClass。
直接破坏委派模型风险更高。
```

## 五、SPI 是什么

SPI：Service Provider Interface。

它是一种服务发现机制：

```text
接口由框架定义，实现由第三方提供，运行时自动发现实现。
```

典型例子：

- JDBC Driver。
- Dubbo SPI。
- Java 日志门面。
- SpringFactoriesLoader。

JDK SPI 使用：

```text
META-INF/services/接口全限定名
```

文件内容：

```text
实现类全限定名
```

加载：

```java
ServiceLoader.load(MyService.class);
```

## 六、SPI 为什么需要线程上下文类加载器

问题：

```text
ServiceLoader 属于 JDK 核心类库，由 Bootstrap ClassLoader 加载。
但服务实现类在应用 classpath 中，由 Application ClassLoader 加载。
```

按照双亲委派，父加载器无法加载子加载器路径下的类。

解决：

```text
使用 Thread.currentThread().getContextClassLoader()
```

也就是让父层代码借助线程上下文类加载器，加载应用层实现。

这就是典型的“父加载器反向使用子加载器”的场景。

## 七、Tomcat 为什么要打破双亲委派

Tomcat 要解决两个问题：

### 1. Web 应用隔离

不同 Web 应用可能依赖不同版本的同一个类库。

例如：

```text
app1 使用 spring 5
app2 使用 spring 6
```

如果完全按双亲委派，隔离会很困难。

### 2. 容器类和应用类分离

Tomcat 自己的类库不应该和 Web 应用类库互相污染。

所以 Tomcat 有自己的类加载体系，让每个 Web 应用有独立的 WebAppClassLoader。

## 八、常见面试追问

### 1. 双亲委派能防止所有核心类被替换吗

不能说“所有”。它主要保证核心类优先由父加载器加载。同时 JVM 对 `java.*` 包也有安全限制。业务仍可能通过自定义类加载器加载其他同名类，但类型隔离和安全限制会影响使用。

### 2. SPI 是否打破双亲委派

SPI 不一定直接破坏 `loadClass` 的委派流程，但它通过线程上下文类加载器实现了父层代码加载子层实现，这是一种对双亲委派限制的绕开。

### 3. 为什么 JDBC Driver 能自动加载

驱动 jar 在 `META-INF/services/java.sql.Driver` 中声明实现类，`DriverManager` 通过 SPI 机制发现并加载驱动。

## 九、生产经验

- 类冲突排查要看 class 由哪个 ClassLoader 加载。
- 插件化系统必须设计清楚父子加载器边界。
- SPI 扩展点要注意实现类初始化成本，避免启动时加载过慢。
- 多版本依赖冲突不是单靠 Maven 排除就一定能解决，容器类加载边界也很关键。

