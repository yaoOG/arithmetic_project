- **类的全限定名**：一个人的姓名（例如：张三）
- **类加载器**：这个人所在的学校或公司（例如：A公司）
- **JVM中的类**：一个具体的人（例如：A公司的张三）

**即使两个类来源于同一个Class文件，只要加载它们的类加载器不同，它们在JVM眼中就是两个完全不同的类。**
```java
// 演示不同类加载器加载同一类的效果

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        // 创建自定义类加载器

        ClassLoader myLoader = new CustomClassLoader();

        // 使用自定义类加载器加载本类

        Object obj = myLoader.loadClass("ClassLoaderTest").newInstance();

        System.out.println(obj.getClass()); 

        // 输出: class ClassLoaderTest

        System.out.println(obj instanceof ClassLoaderTest); 

        // 输出: false (关键结果!)

    }

}
```

  

