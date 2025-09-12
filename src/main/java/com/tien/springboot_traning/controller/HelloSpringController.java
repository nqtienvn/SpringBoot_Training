package com.tien.springboot_traning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // thoong bao day la 1 controller ma map API
public class HelloSpringController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello spring boot";
    }
}
