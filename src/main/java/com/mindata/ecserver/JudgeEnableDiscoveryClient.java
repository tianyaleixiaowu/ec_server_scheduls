package com.mindata.ecserver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * @author wuweifeng wrote on 2017/11/25.
 * 根据部署环境动态决定是否启用eureka
 */
@Component
@ConditionalOnProperty(value = "open.eureka")
@EnableDiscoveryClient
public class JudgeEnableDiscoveryClient {
}
