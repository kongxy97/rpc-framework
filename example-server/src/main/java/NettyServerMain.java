import com.kxy.HelloService;
import com.kxy.annotation.RpcScan;
import com.kxy.config.RpcServiceConfig;
import com.kxy.remoting.transport.netty.server.NettyRpcServer;
import com.kxy.serviceImpl.HelloServiceImpl2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"com.kxy"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = applicationContext.getBean("nettyRpcServer", NettyRpcServer.class);
        // 手动注册HelloServiceImpl2
        HelloService helloService2 = new HelloServiceImpl2();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder().group("test2")
                .version("version2").service(helloService2).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
