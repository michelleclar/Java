package org.carl._chat.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRequestMessage extends Message {

  private String content;
  private String to;
  private String from;

  @Override
  public int getMessageType() {
    return ChatRequestMessage;
  }
}
