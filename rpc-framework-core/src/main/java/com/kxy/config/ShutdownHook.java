package com.kxy.config;

import com.kxy.utils.CuratorUtils;
import com.kxy.utils.threadpool.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Slf4j
public class ShutdownHook {
    private static final ShutdownHook SHUTDOWN_HOOK = new ShutdownHook();
    private static int PORT = 9998;

    public static ShutdownHook getShutdownHook() {
        return SHUTDOWN_HOOK;
    }

    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PORT);
                CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
            } catch (UnknownHostException ignore) {

            }
            ThreadPoolFactoryUtils.shutdownAllThreadPool();
        }));
    }
}
