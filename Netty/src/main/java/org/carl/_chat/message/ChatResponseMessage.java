package org.carl._chat.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage {

  private String from;
  private String content;

  public ChatResponseMessage(boolean success, String reason) {
    super(success, reason);
  }

  public ChatResponseMessage(String from, String content) {
    this.from = from;
    this.content = content;
  }

  @Override
  public int getMessageType() {
    return ChatResponseMessage;
  }
}
