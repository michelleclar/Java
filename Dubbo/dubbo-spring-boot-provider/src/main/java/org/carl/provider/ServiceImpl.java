package org.carl.provider;

import org.carl.service.Service;

public class ServiceImpl implements Service {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
