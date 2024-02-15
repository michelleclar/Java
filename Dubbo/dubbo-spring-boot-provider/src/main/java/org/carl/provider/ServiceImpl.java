package org.carl.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.carl.service.Service;

@DubboService
public class ServiceImpl implements Service {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
