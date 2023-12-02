package org.carl.fix.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class FixedLength {
   // 在服务端 进行 长度限制
//    ch.pipeline().addLast(new FixedLengthFrameDecoder(8));
    // 分隔符 进行 粘包 和 半包处理 自定义 分隔符
//    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
    // 预设长度
//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 1, 0, 1));
   public static void main(String[] args) {
       EmbeddedChannel embeddedChannel = new EmbeddedChannel(
               new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
               new LoggingHandler(LogLevel.DEBUG)
       );
       ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        push(buf,"hello");
        push(buf,"word");

       embeddedChannel.writeInbound(buf);
   }

   static void push(ByteBuf buf,String content){
       byte[] bytes = content.getBytes();
       //大端
       buf.writeInt(bytes.length);
       buf.writeBytes(bytes);
   }


}
