package org.carl._chat.protocol;

import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import org.carl.protocol.common.Proto;

public enum SerializableProto {
  MESSAGE((byte) 0, Proto.Message.getDefaultInstance()),
  PING((byte) 0, Proto.Ping.getDefaultInstance()),
  POING((byte) 0, Proto.Pong.getDefaultInstance()),
  USER((byte) 0, Proto.User.getDefaultInstance());

  final MessageLiteOrBuilder o;
  final byte type;

  SerializableProto(byte type, MessageLiteOrBuilder o) {
    this.type = type;
    this.o = o;
  }

  public MessageLiteOrBuilder getBuild() {
    return o;
  }

  public Parser getParser() {
    return o.getDefaultInstanceForType().getParserForType();
  }

  public byte getType() {
    return type;
  }
}
