package com.lixl.springcloud.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "producer")
public interface ProducerClient {

    @GetMapping("/api/actions/say/hello")
    String getMsg();

}
