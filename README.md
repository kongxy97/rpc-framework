# rpc-framework
## 项目模块
example-client 客户端示例  
example-server 服务端示例  
example-service-api 存放服务接口  
rpc-framework-common 存放枚举和工具类  
rpc-framework-core RPC框架的核心实现类  
&nbsp;&nbsp;|-- annotation 存放注解  
&nbsp;&nbsp;&nbsp;&nbsp;|-- RpcReference 消费服务注解  
&nbsp;&nbsp;&nbsp;&nbsp;|-- RpcScan 包扫描注解  
&nbsp;&nbsp;&nbsp;&nbsp;|-- RpcService 发布服务注解  
&nbsp;&nbsp;|-- compress  
&nbsp;&nbsp;&nbsp;&nbsp;|-- Compress 解压缩序列化数据接口  
&nbsp;&nbsp;&nbsp;&nbsp;|-- gzip.GzipCompressImpl Gzip解压缩实现类  
&nbsp;&nbsp;|-- config  
&nbsp;&nbsp;&nbsp;&nbsp;|-- RpcServiceConfig 表示一个RPC服务  
&nbsp;&nbsp;&nbsp;&nbsp;|-- ShutdownHook 服务关闭时从注册中心注销服务  
&nbsp;&nbsp;|-- loadBalance  
&nbsp;&nbsp;&nbsp;&nbsp;|-- LoadBalance 负载均衡接口  
&nbsp;&nbsp;&nbsp;&nbsp;|-- AbstractLoadBalance 负载均衡抽象类  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- ConsistentHashLoadBalance 一致性哈希算法实现类  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-- RandomLoadBalance 随机负载算法算法实现类  




## 使用

### 服务提供端

实现接口：

```java
@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl implements HelloService {
    static {
        System.out.println("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
	
@Slf4j
public class HelloServiceImpl2 implements HelloService {

    static {
        System.out.println("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}
```

发布服务(使用 Netty 进行传输)：

```java
@RpcScan(basePackage = {"com.kxy"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = applicationContext.getBean("nettyRpcServer", NettyRpcServer.class);
        // HelloServiceImpl2实现类没有使用RpcService注解，需要手动发布
        HelloService helloService2 = new HelloServiceImpl2();
        RpcServiceProperties rpcServiceConfig = RpcServiceProperties.builder()
                .group("test2").version("version2").build();
        nettyServer.registerService(helloService2, rpcServiceConfig);
        nettyServer.start();
    }
}
```

### 服务消费端

```java
@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() throws InterruptedException {
        String hello = this.helloService.hello(new Hello("111", "222"));
        //使用 assert 断言需要在 VM options 添加参数：-ea
        assert "Hello description is 222".equals(hello);
        Thread.sleep(12000);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
```

```java
@RpcScan(basePackage = {"com.kxy"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = applicationContext.getBean("helloController", HelloController.class);
        helloController.test();
    }
}
```  
