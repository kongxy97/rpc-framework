package com.kxy.serviceImpl;

import com.kxy.Hello;
import com.kxy.HelloService;
import com.kxy.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl implements HelloService {
    static {
        log.info("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDesc();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
