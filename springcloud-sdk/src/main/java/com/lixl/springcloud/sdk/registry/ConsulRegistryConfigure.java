package com.lixl.springcloud.sdk.registry;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.ConsulClient;


/**
 * @author lixinglin
 * @description
 * @create 2019/9/10 3:08 下午
 * @since jdk 1.8
 */
@Configuration
@EnableConfigurationProperties({RegistryProperties.class})
@ConditionalOnProperty(value = "registry.consul", havingValue = "true", matchIfMissing = true)
public class ConsulRegistryConfigure {

    @Bean
    public ConsulClient consulClient(RegistryProperties registryProperties, ConsulDiscoveryProperties consulDiscoveryProperties) {
        final int agentPort = registryProperties.getPort();
        final String agentHost = !StringUtils.isEmpty(registryProperties.getScheme())
                ? registryProperties.getScheme() + "://" + registryProperties.getHost()
                : registryProperties.getHost();

        consulDiscoveryProperties.setPreferIpAddress(registryProperties.isPreferIpAddress());
        consulDiscoveryProperties.setScheme(registryProperties.getScheme());
        consulDiscoveryProperties.setHealthCheckInterval(registryProperties.getHealthCheckInterval());
        consulDiscoveryProperties.setEnabled(registryProperties.isConsul());
        if (StringUtils.isNotBlank(registryProperties.getHealthCheckPath())) {
            consulDiscoveryProperties.setHealthCheckPath(registryProperties.getHealthCheckPath());
        }
        if (StringUtils.isNotBlank(registryProperties.getInstanceId())) {
            consulDiscoveryProperties.setInstanceId(registryProperties.getInstanceId());
        }
        if (StringUtils.isNotBlank(registryProperties.getIpAddress())) {
            consulDiscoveryProperties.setIpAddress(registryProperties.getIpAddress());
        }
        List<String> tags = new ArrayList<>();
        tags.add("registerTime=" + System.currentTimeMillis());
        if (registryProperties.getTags() != null) {
            tags.addAll(registryProperties.getTags());
        }
        consulDiscoveryProperties.setTags(tags);


        if (registryProperties.getTls() != null) {
            ConsulProperties.TLSConfig tls = registryProperties.getTls();
            TLSConfig tlsConfig = new TLSConfig(tls.getKeyStoreInstanceType(),
                    tls.getCertificatePath(), tls.getCertificatePassword(),
                    tls.getKeyStorePath(), tls.getKeyStorePassword());
            return new ConsulClient(agentHost, agentPort, tlsConfig);
        }
        return new ConsulClient(agentHost, agentPort);
    }

}
