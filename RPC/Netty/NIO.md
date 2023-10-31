# NIO
> jdk 1.4 之后加入
> 非阻塞IO
### Channel
> 数据传输 双向通道（http2 使用的也是双向 格式是二进制）
> 
1. FileChannel 文件
2. DatagramChannel UDP 
3. SocketChannel TCP 
4. ServerSocketChannel TCP 服务器端
> 
### Buffer
>
1. ByteBuffer
   1. MappedByteBuffer
   2. DirectByteBuffer
   3. HeapByteBuffer
2. ShortBuffer
3. IntBuffer
4. LongBuffer
5. FloatBuffer
6. DoubleBuffer
7. CharBuffer
```text
capacity
position
limit
写模式 
position 写入位置 也就是 数组 有值的最后一位 
在能充满数组时 capacity 等于 position
不能充满时 position 一定小于 capacity
此时limit 为写入限制 等于 capacity
读模式
limit 变为读取限制
position 变为读取位置
clear 会覆盖写 
compact 不会覆盖写
```

>
### Selector
> 多路复用(优化线程池思想) 采用响应式处理 NIO 表现为事件
```
thread --->  Selector ---->  Channel,Channel,Channel
````

### BIO vs  NIO
> 二者都是全双工 读写同时进行
stream 不会自动缓冲数据 stream仅支持阻塞
### IO 模型
> 同步阻塞，同步非阻塞，多路复用，异步阻塞，异步非阻塞
> 阻塞IO 非阻塞IO 多路复用 信号驱动 异步IO

> 用户态 ---> 内核态
阻塞IO
> 用户read ——————> 内核 等待数据 复制数据
非阻塞IO
> read 会不断访问内核态 但在复制数据时还是会阻塞 只有在读的时候才不会阻塞
多路复用
> selector.select ----> 等待数据（阻塞） even 驱动 read ------> 复制数据（阻塞）
> 等待事件 会阻塞（select）

### 传统 IO 
```java
读取文件
File f = new Fiel(path);
RandomAccessFile file = new RandomAccessFile(f,'r');

byte[] buf = new Byte[(int)f.length()];
file.read(buf);
socket socket = ...;
socket.getOutputStream().write(buf);
```
> read(磁盘 内核缓冲区 用户缓冲区) 用户态---》内核态----》用户态 
> write（用户缓存---》socket缓冲区---》网卡）用户态到内核态
> 拷贝4次

DirectByteBuffer 会共用缓存区
