package com.lixl.springcloud.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConsumerController {

    @Value("${registry.url}")
    private String registryUrl;

    @GetMapping("/registry/url")
    public String getRegistryUrl() {
        return registryUrl;
    }
}
