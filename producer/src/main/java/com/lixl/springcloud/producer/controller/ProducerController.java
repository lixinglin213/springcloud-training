package com.lixl.springcloud.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProducerController {

    @GetMapping("/actions/say/hello")
    public String getMessage() {
        return "hello";
    }

    @GetMapping("/say/{something}")
    public String say(@PathVariable("something") String something) {
        return something;
    }

}
