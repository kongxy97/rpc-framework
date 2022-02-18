package com.kxy.registry.zk;

import com.kxy.registry.ServiceRegistry;
import com.kxy.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class ZkServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + "/" + inetSocketAddress.toString();
        CuratorUtils.createPersistentNode(CuratorUtils.getZkClient(), servicePath);
    }
}
