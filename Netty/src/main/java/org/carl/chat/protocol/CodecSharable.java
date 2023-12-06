package org.carl.chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.carl.chat.config.Config;
import org.carl.chat.message.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CodecSharable extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outL) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 魔术
        out.writeBytes(new byte[]{'c', 'a', 'r', 'l'});
        // 版本
        out.writeByte(1);
        //序列化
        out.writeByte(Config.getSerializerAlgorithm().ordinal());
        //指令类型
        out.writeByte(msg.getMessageType());
        //请求序列（全双工 异步）
        out.writeInt(msg.getSequenceId());
        //对齐填充
        out.writeByte(0xff);
        //字节数组
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        //消息长度
        out.writeInt(bytes.length);
        //写入内容
        out.writeBytes(bytes);
        outL.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int i = in.readInt();
        byte v = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Serializer.Algorithm values = Serializer.Algorithm.values()[serializerType];
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        Message m = values.deserialize(messageClass, bytes);
        log.debug("{}, {}, {}, {}, {},{}", i, v, serializerType, messageType, sequenceId, length);
        log.debug("{}", m);
        out.add(m);
    }
}
