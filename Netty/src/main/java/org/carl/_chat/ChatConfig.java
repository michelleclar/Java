package org.carl._chat;

public class ChatConfig {
  int port;
  int maxConnect;
  String host;

  public ChatConfig(int port, int maxConnect, String host) {
    this.port = port;
    this.maxConnect = maxConnect;
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public int getMaxConnect() {
    return maxConnect;
  }

  public String getHost() {
    return host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setMaxConnect(int maxConnect) {
    this.maxConnect = maxConnect;
  }

  public void setHost(String host) {
    this.host = host;
  }
}
