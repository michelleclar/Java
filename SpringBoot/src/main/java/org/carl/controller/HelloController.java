package org.carl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Hello World";
    }

    @GetMapping("/t")
    public String sayHello(@RequestParam("url") String url) {
        System.out.println(url);
//    return restTemplate.getForObject("http://basic-provider/sayHello?name={1}", String.class, name);
        return url;
    }
}
