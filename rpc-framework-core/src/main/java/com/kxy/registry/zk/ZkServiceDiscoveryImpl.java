package com.kxy.registry.zk;

import com.kxy.enums.RpcErrorMessageEnum;
import com.kxy.exception.RpcException;
import com.kxy.extension.ExtensionLoader;
import com.kxy.loadBalance.LoadBalance;
import com.kxy.registry.ServiceDiscovery;
import com.kxy.remoting.dto.RpcRequest;
import com.kxy.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        List<String> serviceList = CuratorUtils.getChildrenNodes(CuratorUtils.getZkClient(), rpcServiceName);
        if (serviceList == null || serviceList.isEmpty()) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        String targetServiceURL = loadBalance.selectServiceAddress(serviceList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceURL);
        String[] address = targetServiceURL.split(":");
        String host = address[0];
        int port = Integer.parseInt(address[1]);
        return new InetSocketAddress(host, port);
    }
}
