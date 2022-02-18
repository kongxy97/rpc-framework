package com.kxy.remoting.transport;

import com.kxy.extension.SPI;
import com.kxy.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {
    Object sendRpcRequest(RpcRequest request);
}
