package org.carl.chat.server.service;


import org.carl.chat.config.Config;

import java.util.Map;

public class ServicesFactory {

    static Map<Class<?>, Object> map = Config.getServicesMap();

    public static <T> T getService(Class<T> interfaceClass) {
        return (T) map.get(interfaceClass);
    }
}
