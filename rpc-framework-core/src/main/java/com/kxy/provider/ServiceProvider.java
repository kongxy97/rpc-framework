package com.kxy.provider;

import com.kxy.config.RpcServiceConfig;

public interface ServiceProvider {
    void addService(RpcServiceConfig config);

    Object getService(String rpcServiceName);

    void publishService(RpcServiceConfig config);
}
