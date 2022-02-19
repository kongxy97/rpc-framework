package com.kxy;

import com.kxy.annotation.RpcReference;
import org.springframework.stereotype.Component;

@Component
public class HelloController {
    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() throws InterruptedException {
        String hello = helloService.hello(new Hello("111", "222"));
        //使用 assert 断言需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        Thread.sleep(10000);
        for (int i = 0;i < 10; ++i) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
