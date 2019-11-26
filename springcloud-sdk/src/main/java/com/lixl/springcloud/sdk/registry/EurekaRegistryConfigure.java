package com.lixl.springcloud.sdk.registry;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadata;
import org.springframework.cloud.netflix.eureka.metadata.ManagementMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;


/**
 * @author lixinglin
 * @description
 * @create 2019/11/4 11:27 上午
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties({RegistryProperties.class})
@ConditionalOnProperty(value = "registry.eureka", havingValue = "true", matchIfMissing = true)
public class EurekaRegistryConfigure {

    @Autowired
    private EurekaClientConfigBean eurekaClientConfigBean;

    @Autowired
    private RegistryProperties registryProperties;

    /**
     * eureka registry url
     */
    private String DEFAULT_ZONE = "http://%s:%s/eureka/";

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public void setEurekaClientConfigBean() {
        HashMap<String, String> defaultZone = new HashMap<>(2);
        defaultZone.put("defaultZone", String.format(DEFAULT_ZONE, registryProperties.getHost(), registryProperties.getPort()));
        eurekaClientConfigBean.setServiceUrl(defaultZone);
    }

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils,
                                                             ManagementMetadataProvider managementMetadataProvider) {
        String hostname = getProperty("eureka.instance.hostname");
//        String ipAddress = getProperty("eureka.instance.ip-address");
        boolean isSecurePortEnabled = Boolean
                .parseBoolean(getProperty("eureka.instance.secure-port-enabled"));

        String serverContextPath = env.getProperty("server.servlet.context-path", "/");
        int serverPort = Integer
                .valueOf(env.getProperty("server.port", env.getProperty("port", "8080")));

        Integer managementPort = env.getProperty("management.server.port", Integer.class);
        String managementContextPath = env
                .getProperty("management.server.servlet.context-path");
        Integer jmxPort = env.getProperty("com.sun.management.jmxremote.port",
                Integer.class);
        EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);

        instance.setNonSecurePort(serverPort);
        instance.setInstanceId(registryProperties.getInstanceId());
        instance.setPreferIpAddress(registryProperties.isPreferIpAddress());
        instance.setSecurePortEnabled(isSecurePortEnabled);


        if (StringUtils.hasText(registryProperties.getIpAddress())) {
            instance.setIpAddress(registryProperties.getIpAddress());
        }

        if (isSecurePortEnabled) {
            instance.setSecurePort(serverPort);
        }

        if (StringUtils.hasText(hostname)) {
            instance.setHostname(hostname);
        }
        String statusPageUrlPath = getProperty("eureka.instance.status-page-url-path");
        String healthCheckUrlPath = getProperty("eureka.instance.health-check-url-path");

        if (StringUtils.hasText(statusPageUrlPath)) {
            instance.setStatusPageUrlPath(statusPageUrlPath);
        }
        if (StringUtils.hasText(healthCheckUrlPath)) {
            instance.setHealthCheckUrlPath(healthCheckUrlPath);
        }

        ManagementMetadata metadata = managementMetadataProvider.get(instance, serverPort,
                serverContextPath, managementContextPath, managementPort);

        if (metadata != null) {
            instance.setStatusPageUrl(metadata.getStatusPageUrl());
            instance.setHealthCheckUrl(metadata.getHealthCheckUrl());
            if (instance.isSecurePortEnabled()) {
                instance.setSecureHealthCheckUrl(metadata.getSecureHealthCheckUrl());
            }
            Map<String, String> metadataMap = instance.getMetadataMap();
            metadataMap.computeIfAbsent("management.port",
                    k -> String.valueOf(metadata.getManagementPort()));
        } else {
            if (StringUtils.hasText(managementContextPath)) {
                instance.setHealthCheckUrlPath(
                        managementContextPath + instance.getHealthCheckUrlPath());
                instance.setStatusPageUrlPath(
                        managementContextPath + instance.getStatusPageUrlPath());
            }
        }

        setupJmxPort(instance, jmxPort);
        return instance;
    }

    private String getProperty(String property) {
        return this.env.containsProperty(property) ? this.env.getProperty(property) : "";
    }

    private void setupJmxPort(EurekaInstanceConfigBean instance, Integer jmxPort) {
        Map<String, String> metadataMap = instance.getMetadataMap();
        if (metadataMap.get("jmx.port") == null && jmxPort != null) {
            metadataMap.put("jmx.port", String.valueOf(jmxPort));
        }
    }

}
