package org.carl._chat;

import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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


  public void setPort(int port) {
    this.port = port;
  }

  public void setMaxConnect(int maxConnect) {
    this.maxConnect = maxConnect;
  }

  public void setHost(String host) {
    this.host = host;
  }

  static Map<String, Object> configServerMap;
  static Map<String, String> InterfaceMap;

  static {
    Yaml yaml = new Yaml();
    Map<String, Object> configMap =
        yaml.load(ChatConfig.class.getClassLoader().getResourceAsStream("config.yml"));
    configServerMap = (Map<String, Object>) configMap.get("server");
    InterfaceMap = (Map<String, String>) configServerMap.get("services");
  }

  public static int getServerPort() {
    return Optional.ofNullable((Integer) configServerMap.get("port")).orElse(8080);
  }

  public static String getHost() {
    return Optional.ofNullable((String) configServerMap.get("host")).orElse("127.0.0.1");
  }

  public static int getMaxConnect(){

    return Optional.ofNullable((Integer) configServerMap.get("maxConnect")).orElse(8080);
  }

  public static Map<Class<?>, Object> getServicesMap() {
    Map<Class<?>, Object> map = new ConcurrentHashMap<>();
    InterfaceMap.forEach((k, v) -> {
      Class<?> interfaceClass = null;
      Class<?> instanceClass = null;
      try {
        instanceClass = Class.forName(v);
        interfaceClass = Class.forName(k);
        map.put(interfaceClass, instanceClass.getDeclaredConstructor().newInstance());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }

    });
    return map;

  }

  public static void main(String[] args) {
    System.out.println(getServerPort());
    System.out.println(getHost());
    System.out.println(getServicesMap());
  }
}
