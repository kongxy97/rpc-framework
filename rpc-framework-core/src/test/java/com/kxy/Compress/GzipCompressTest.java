package com.kxy.Compress;

import com.kxy.compress.Compress;
import com.kxy.compress.gzip.GzipCompressImpl;
import com.kxy.remoting.dto.RpcRequest;
import com.kxy.serialize.kryo.KryoSerializerImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GzipCompressTest {
    @Test
    void gzipCompressTest() {
        Compress gzipCompress = new GzipCompressImpl();
        RpcRequest rpcRequest = RpcRequest.builder().methodName("hello")
                .parameters(new Object[]{"sayhelooloo", "sayhelooloosayhelooloo"})
                .interfaceName("com,kxy.HelloService")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .group("group1")
                .version("version1")
                .build();
        KryoSerializerImpl kryoSerializer = new KryoSerializerImpl();
        byte[] rpcRequestBytes = kryoSerializer.serialize(rpcRequest);
        byte[] compressRpcRequestBytes = gzipCompress.compress(rpcRequestBytes);
        byte[] decompressRpcRequestBytes = gzipCompress.decompress(compressRpcRequestBytes);
        assertEquals(rpcRequestBytes.length, decompressRpcRequestBytes.length);
    }
}
