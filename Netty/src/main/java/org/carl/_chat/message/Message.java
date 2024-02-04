package org.carl._chat.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public abstract class Message implements Serializable {

  /**
   * 根据消息类型字节，获得对应的消息 class
   *
   * @param messageType 消息类型字节
   * @return 消息 class
   */
  public static Class<? extends Message> getMessageClass(int messageType) {
    return messageClasses.get(messageType);
  }

  private int sequenceId;

  private int messageType;

  public abstract int getMessageType();


  public static final int ChatRequestMessage = 0;
  public static final int ChatResponseMessage = 1;
  public static final int PingMessage = 10;
  public static final int PongMessage = 11;

  /** 请求类型 byte 值 */
  public static final int RPC_MESSAGE_TYPE_REQUEST = 101;

  /** 响应类型 byte 值 */
  public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

  private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

  static {
    messageClasses.put(ChatRequestMessage, ChatRequestMessage.class);
    messageClasses.put(ChatResponseMessage, ChatResponseMessage.class);
    messageClasses.put(PingMessage, org.carl.chat.message.PingMessage.class);
    messageClasses.put(PongMessage, org.carl.chat.message.PongMessage.class);
  }
}
