package com.kxy.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {
    private String version = "";

    private String group = "";

    private Object service;

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }

    public String getRpcServiceName() {
        return getServiceName() + this.group + this.version;
    }
}
