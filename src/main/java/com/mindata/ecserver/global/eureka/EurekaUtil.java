package com.mindata.ecserver.global.eureka;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
@Component
public class EurekaUtil {
    @Resource
    private DiscoveryClient discoveryClient;

    public void check() {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("");

    }
}
