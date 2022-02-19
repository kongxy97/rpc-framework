package com.kxy.serviceImpl;

import com.kxy.Hello;
import com.kxy.HelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl2 implements HelloService {
    static {
        log.info("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDesc();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}
