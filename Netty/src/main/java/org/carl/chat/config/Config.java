package org.carl.chat.config;


import org.carl.chat.protocol.Serializer;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Config {
    static Map<String, Object> configServerMap;
    static Map<String, String> InterfaceMap;

    static {
        Yaml yaml = new Yaml();
        Map<String, Object> configMap =
                yaml.load(Config.class.getClassLoader()
                        .getResourceAsStream("config.yml"));
        configServerMap = (Map<String, Object>) configMap.get("server");
        InterfaceMap = (Map<String, String>) configServerMap.get("services");
    }

    public static int getServerPort() {
        return Optional.ofNullable((Integer) configServerMap.get("port")).orElse(8080);
    }


    public static Serializer.Algorithm getSerializerAlgorithm() {
        Map<String, Object> serializer = (Map<String, Object>) configServerMap.get("serializer");
        String r = Optional.ofNullable((String) serializer.get("algorithm")).orElse("Java");
        return Serializer.Algorithm.valueOf(r);
    }

    public static Map<Class<?>, Object> getServicesMap() {
        Map<Class<?>, Object> map = new ConcurrentHashMap<>();
        InterfaceMap.forEach((k, v) -> {
            Class<?> interfaceClass = null;
            Class<?> instanceClass = null;
            try {
                instanceClass = Class.forName(v);
                interfaceClass = Class.forName(k);
                map.put(interfaceClass, instanceClass.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        });
        return map;

    }

    //
    public static void main(String[] args) {
        System.out.println(getSerializerAlgorithm());
        System.out.println(getServerPort());

        System.out.println(getServicesMap());
    }
}
