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



