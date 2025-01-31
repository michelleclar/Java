package org.carl.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.carl.service.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private Service service;

    @Override
    public void run(String... args) throws Exception {
        String result = service.sayHello("world");
        System.out.println("Receive result ======> " + result);
        new Thread(()-> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println(new Date() + " Receive result ======> " + service.sayHello("world"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
