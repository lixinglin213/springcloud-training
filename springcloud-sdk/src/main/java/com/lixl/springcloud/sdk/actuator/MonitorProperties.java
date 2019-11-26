package com.lixl.springcloud.sdk.actuator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.actuate.health.ShowDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lixinglin
 * @description
 * @create 2019/10/8 4:02 下午
 * @since jdk 1.8
 */
@ConfigurationProperties(prefix = "monitor.actuator")
@Setter
@Getter
public class MonitorProperties {

    /**Endpoint IDs that should be included or '*' for all,default '"*"'.*/
    private Set<String> include = new LinkedHashSet<>(Arrays.asList("*"));


    /**Endpoint IDs that should be excluded or '*' for all.*/
    private Set<String> exclude = new LinkedHashSet<>();

    /**When to show full health details,default 'always'.*/
    private ShowDetails showDetails = ShowDetails.ALWAYS;

    /**
     * Roles used to determine whether or not a user is authorized to be shown details.
     * When empty, all authenticated users are authorized.
     */
    private Set<String> roles = new HashSet<>();

}
