package com.kxy.loadBalance;

import com.kxy.extension.SPI;
import com.kxy.remoting.dto.RpcRequest;

import java.util.List;

@SPI
public interface LoadBalance {
    String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest);
}
