package com.lixl.springcloud.sdk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;

/**
 * @author lixinglin
 * @description
 * @create 2019/10/31 11:07 上午
 * @since jdk 1.8
 */
@Configuration
@ConditionalOnProperty(value = "registry.consul", havingValue = "true", matchIfMissing = true)
public class ConsulHealthCheckConfiguration {

    @Bean
    public ServerListFilter<Server> ribbonServerListFilter() {
        return new ConsulHealthCheckFilter();
    }

}
