package com.kxy.remoting.transport.socket;

import com.kxy.factory.SingletonFactory;
import com.kxy.remoting.dto.RpcRequest;
import com.kxy.remoting.dto.RpcResponse;
import com.kxy.remoting.handler.RpcRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable {
    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        log.info("server handle message from client by thread: [{}]", Thread.currentThread().getName());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestHandler.handle(request);
            objectOutputStream.writeObject(RpcResponse.success(request, request.getRequestId()));
            objectOutputStream.flush();
        }catch (IOException | ClassNotFoundException e) {
            log.error("occur exception:", e);
        }
    }
}
