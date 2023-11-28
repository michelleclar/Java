# Netty

### 概述

Netty 是一个异步的基于事件驱动的网络应用程序框架，用于快速开发可维护的高性能协议服务器和客户端。

> 4.x 2013

> · Cassandra-nosql
>
> · Spark-大数据分布式计算框架
>
> · Hadoop-大数据分布式存储框架
>
> · RocketMQ-all开源消息队列
>
> · ElasticSearch
>
> · gRPC
>
> · Dubbo
>
> · Spring5.x -flux 不使用tomcat
>
> · Zookeeper

### 组件

#### EventLoop

> EventLoop 单线程执行器（同时维护了一个Selector），里面有run方法处理channel上源源不断的io事件

继承关系

j.u.c.ScheduledExecutorService(Java)

OrderedEventExecutor(Netty)有序处理

事件循环组

EventLoopGroup 

Cannel一般调用EventLoopGroup的register 方法来绑定其中一个EventLoop，后续这个Channel的io事件都由此EventLoop来处理，（保证了io事件处理时的线程安全）

```java
 // 默认线程数       
 static {
     DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
     "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

     if (logger.isDebugEnabled()) {
     logger.debug("-Dio.netty.eventLoopThreads: {}", DEFAULT_EVENT_LOOP_THREADS);
     }
 }     
```

> handler 是如何执行的
>
> 如果两个handler 是同一个线程 那么就直接调用否则 把要调用的代码封装为一个任务对象，由下一个handle的线程来调用

```java
static void invokeChannelRead(final AbstractChannelHandlerContext next, Object msg) {
    final Object m = next.pipeline.touch(ObjectUtil.checkNotNull(msg, "msg"), next);
    //下一个handle 的事件循环是否与当前的事件循环是同一个线程
    EventExecutor executor = next.executor();//返回下一个handler的eventLoop
    //判断下一个线程和当前线程是否同一个是就直接调用
    if (executor.inEventLoop()) {//当前handle线程 是否和eventLoop 是同一个线程
        next.invokeChannelRead(m);
    } else {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                next.invokeChannelRead(m);
            }
        });
    }
}
```

#### Channel

> api
>
> * close() 可以用来关闭channel
> * closeFuture()用来处理channel的关闭
>   * sync()同步等待channel关闭
>   * addListener()异步等待channel关闭
> * pipline()添加处理器
> * write()将数据写入
> * writeAndFlush()将数据写入并刷出




```text
netty 提升的是吞吐量，将任务进行拆分，多个线程执行同一个task

```
