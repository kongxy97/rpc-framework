package com.kxy.remoting.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class RpcMessage {
    private byte messageType;
    // 序列化类型，kryo或protobuff
    private byte codec;
    // 压缩类型
    private byte compress;

    private int requestId;

    private Object data;
}
