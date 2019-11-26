package com.lixl.springcloud.sdk.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.health.HealthStatusHttpMapper;
import org.springframework.boot.actuate.health.HealthWebEndpointResponseMapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinglin
 * @description
 * @create 2019/10/8 4:04 下午
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties({MonitorProperties.class})
@AutoConfigureBefore(WebEndpointAutoConfiguration.class)
public class MonitorConfigure {

    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private MonitorProperties monitorProperties;


    @Bean
    public HealthWebEndpointResponseMapper healthWebEndpointResponseMapper(HealthStatusHttpMapper statusHttpMapper,
                                                                           MonitorProperties properties) {
        return new HealthWebEndpointResponseMapper(statusHttpMapper, properties.getShowDetails(),
                properties.getRoles());
    }

    @Bean
    public void monitorHandler() {
        if (monitorProperties.getInclude().size() > 0) {
            webEndpointProperties.getExposure().setInclude(monitorProperties.getInclude());
        }
        if (monitorProperties.getExclude().size() > 0) {
            webEndpointProperties.getExposure().setExclude(monitorProperties.getExclude());
        }
    }

}
