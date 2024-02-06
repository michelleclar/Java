package org.carl._chat.protocol;

import com.google.protobuf.MessageLiteOrBuilder;
import org.carl.protocol.common.Proto;

public enum SerializableProto {
  MESSAGE(Proto.Message.getDefaultInstance()),
  PING(Proto.Ping.getDefaultInstance()),
  POING(Proto.Pong.getDefaultInstance());


  MessageLiteOrBuilder msg;

  SerializableProto(MessageLiteOrBuilder msg) {

    this.msg = msg;
  }

}
