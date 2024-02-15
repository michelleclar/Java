package org.carl._chat.protocol;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class Codec extends MessageToMessageCodec<ByteBuf, MessageLiteOrBuilder> {

  @Override
  protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) {
    log.debug("encode");

    ByteBuf _out = ctx.alloc().buffer();
    // TODO:edit enmu to Serializable
    _out.writeByte(getMessageType(msg));
    out.add(_out);
    if (msg instanceof MessageLite) {
      out.add(Unpooled.wrappedBuffer(((MessageLite) msg).toByteArray()));
    } else if (msg instanceof MessageLite.Builder) {
      out.add(Unpooled.wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray()));
    }
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws InvalidProtocolBufferException {
    log.debug("decode");
    byte messageType = msg.readByte();

    int length = msg.readableBytes();
    byte[] array;
    int offset;
    if (msg.hasArray()) {
      array = msg.array();
      offset = msg.arrayOffset() + msg.readerIndex();
    } else {
      array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
      offset = 0;
    }
    if (messageType == SerializableProto.USER.type)
      out.add(SerializableProto.USER.getParser().parseFrom(array, offset, length));
    if (messageType == SerializableProto.PING.type)
      out.add(SerializableProto.PING.getParser().parseFrom(array, offset, length));
    if (messageType == SerializableProto.POING.type)
      out.add(SerializableProto.POING.getParser().parseFrom(array, offset, length));
    if (messageType == SerializableProto.MESSAGE.type)
      out.add(SerializableProto.MESSAGE.getParser().parseFrom(array, offset, length));
  }

  // Get message type based on the message object
  byte getMessageType(MessageLiteOrBuilder msg) {

    // User DEFAULT_INSTANCE = User.getDefaultInstance();
    // DEFAULT_INSTANCE.newBuilderForType();
    switch (msg.getClass().getSimpleName()) {
      case "User":
      case "User$Builder":
        return SerializableProto.USER.type; // User message type
      case "Ping":
      case "Ping$Builder":
        return SerializableProto.PING.type; // Ping message type
      case "Pong":
      case "Pong$Builder":
        return SerializableProto.POING.type; // Pong message type
      case "Message":
      case "Message$Builder":
        return SerializableProto.MESSAGE.type;
      default:
        throw new IllegalArgumentException(
            "Unsupported message type: " + msg.getClass().getSimpleName());
    }
  }
}
