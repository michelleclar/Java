package org.carl._chat;

public class ChatConfig {
  int port;
  int maxConnect;

  public ChatConfig(int port, int maxConnect) {
    this.port = port;
    this.maxConnect = maxConnect;
  }

  public int getPort() {
    return port;
  }

  public int getMaxConnect() {
    return maxConnect;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setMaxConnect(int maxConnect) {
    this.maxConnect = maxConnect;
  }
}
