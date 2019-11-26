package com.lixl.springcloud.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lixl.springcloud.consumer.feign.ProducerClient;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProducerClient producerClient;

    @GetMapping("/ribbon/sayHello")
    public String ribbonInvoke() {
        return restTemplate.getForObject("http://producer/api/actions/say/hello", String.class);
    }

    @GetMapping("/feign/sayHello")
    public String feignInvoke() {
        return producerClient.getMsg();
    }

    @GetMapping("/hello")
    private String test() {
        return "hello2";
    }
}
