package com.mindata.ecserver.global.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuweifeng wrote on 2017/12/20.
 */
@Configuration
@ConditionalOnProperty(value = "open.eureka")
public class CuratorClient {
    @Value("${zookeeper.server}")
    private String connectString;
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public CuratorFramework client() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .namespace(applicationName)
                .build();
        client.start();
        return client;
    }

}
