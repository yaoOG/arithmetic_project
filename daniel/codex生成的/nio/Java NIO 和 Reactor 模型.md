# Java NIO 和 Reactor 模型

## 一、先说结论

Java NIO 的核心是：

```text
Channel + Buffer + Selector
```

它通过非阻塞 IO 和多路复用，让一个线程可以管理多个连接。

Reactor 模型的核心是：

```text
事件来了不直接阻塞等待，而是由 Reactor 监听事件，再分发给 Handler 处理。
```

Netty 就是基于 NIO 和 Reactor 思想构建的高性能网络框架。

## 二、BIO、NIO、AIO 区别

| 模型 | 特点 | 问题 |
|---|---|---|
| BIO | 阻塞 IO，一个连接通常一个线程 | 连接多时线程数爆炸 |
| NIO | 非阻塞 IO，多路复用 | 编程复杂 |
| AIO | 异步 IO，完成后回调 | Linux 下应用相对少 |

BIO 模型：

```text
accept 阻塞
read 阻塞
一个连接一个线程
```

NIO 模型：

```text
连接注册到 Selector
一个线程监听多个 Channel
有事件再处理
```

## 三、Channel

Channel 类似通道，数据可以从 Channel 读到 Buffer，也可以从 Buffer 写到 Channel。

常见 Channel：

- FileChannel。
- SocketChannel。
- ServerSocketChannel。
- DatagramChannel。

与传统 Stream 不同：

```text
Stream 是单向的，Channel 通常是双向的。
```

## 四、Buffer

Buffer 是 NIO 中的数据容器。

核心字段：

- capacity：容量。
- position：当前位置。
- limit：读写边界。
- mark：标记位置。

写模式切读模式：

```java
buffer.flip();
```

清空准备继续写：

```java
buffer.clear();
```

未读完压缩：

```java
buffer.compact();
```

## 五、Selector

Selector 是多路复用器。

它可以监听多个 Channel 的事件：

- OP_ACCEPT。
- OP_CONNECT。
- OP_READ。
- OP_WRITE。

流程：

```text
1. Channel 设置非阻塞。
2. Channel 注册到 Selector。
3. Selector.select() 等待事件。
4. 遍历 selectedKeys。
5. 根据事件类型处理。
```

## 六、select、poll、epoll

### select

缺点：

- 文件描述符数量有限。
- 每次需要拷贝 fd 集合。
- 需要遍历查找就绪 fd。

### poll

相比 select 没有固定 fd 数量限制，但仍需要遍历。

### epoll

Linux 高性能多路复用。

优点：

- 事件驱动。
- 不需要每次全量遍历。
- 适合大量连接。

## 七、Reactor 单线程模型

```text
一个 Reactor 线程
-> 监听连接事件
-> 监听读写事件
-> 执行业务处理
```

优点：

- 简单。

缺点：

- 一个线程处理所有事情，业务稍慢就阻塞整个系统。

## 八、Reactor 多线程模型

```text
一个 Reactor 负责 IO 事件
业务处理交给 worker 线程池
```

优点：

- IO 和业务处理分离。

缺点：

- Reactor 单点仍可能成为瓶颈。

## 九、主从 Reactor 模型

```text
Boss Reactor：处理 accept。
Worker Reactor：处理 read/write。
业务线程池：处理业务逻辑。
```

Netty 常用这种思想：

- bossGroup：接收连接。
- workerGroup：处理 IO 读写。
- business executor：处理耗时业务。

## 十、Netty 线程模型

Netty 中：

```text
EventLoopGroup
-> EventLoop
-> Channel
```

一个 EventLoop 通常绑定一个线程，一个 Channel 注册到一个 EventLoop 后，后续 IO 事件都由这个 EventLoop 处理。

好处：

```text
同一个 Channel 的 IO 操作串行执行，减少锁竞争。
```

## 十一、粘包和拆包

TCP 是字节流协议，没有消息边界。

所以可能出现：

- 多个消息粘在一起。
- 一个消息被拆成多段。

解决方式：

- 固定长度。
- 分隔符。
- 消息头 + 消息体长度。
- Netty 解码器，如 LengthFieldBasedFrameDecoder。

## 十二、零拷贝

Netty 零拷贝主要包括：

- DirectBuffer 减少堆内外拷贝。
- CompositeByteBuf 组合多个 Buffer。
- FileRegion 使用 sendfile。
- slice/duplicate 避免内存复制。

注意：

```text
不同语境下零拷贝含义不同，有的是 OS 层减少用户态/内核态拷贝，有的是框架层避免 byte[] 复制。
```

## 十三、生产经验

- Netty IO 线程不要执行耗时业务。
- ByteBuf 使用后要正确释放，避免直接内存泄漏。
- 所有网络调用必须设置超时。
- 编解码器要处理半包和异常包。
- 心跳机制要和业务超时区分。
- 背压很重要，不能无限读入内存。

## 十四、常见面试追问

### 1. NIO 为什么能支撑高并发连接

因为它使用非阻塞 Channel 和 Selector 多路复用，一个线程可以监听多个连接，避免 BIO 一个连接一个线程的巨大开销。

### 2. Netty 为什么快

Reactor 线程模型、高效事件循环、DirectBuffer、对象池、零拷贝思想、优秀编解码和内存管理共同作用。

### 3. TCP 粘包是 TCP 的问题吗

不是 bug，而是 TCP 字节流协议没有消息边界。应用层协议必须自己定义边界。

