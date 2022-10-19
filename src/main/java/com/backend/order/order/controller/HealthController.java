package com.backend.order.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    // 생략
    @GetMapping("/info")
    public String info(@Value("${server.port}") String port) {
        return "서비스의 기본 동작 Port: {" + port + "}";
    }
}
