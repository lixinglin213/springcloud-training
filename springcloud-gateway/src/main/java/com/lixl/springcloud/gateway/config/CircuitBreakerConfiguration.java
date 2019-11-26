//package com.lixl.springcloud.springcloudgateway.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.util.concurrent.TimeoutException;
//
//@Configuration
//public class CircuitBreakerConfiguration {
//    // Create a custom configuration for a CircuitBreaker
//
//    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//            .failureRateThreshold(50)
//            .waitDurationInOpenState(Duration.ofMillis(1000))
//            .ringBufferSizeInHalfOpenState(2)
//            .ringBufferSizeInClosedState(2)
//            .recordExceptions(IOException.class, TimeoutException.class)
////            .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
//            .build();
//    CircuitBreaker circuitBreaker = CircuitBreaker.of("MICROSERVICES", circuitBreakerConfig);
//
//
//    /*// Create a CircuitBreakerRegistry with a custom global configuration
//    CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
//
//    // Get a CircuitBreaker from the CircuitBreakerRegistry with the global default configuration
//    CircuitBreaker circuitBreaker2 = circuitBreakerRegistry.circuitBreaker("otherName");
//
//    // Get a CircuitBreaker from the CircuitBreakerRegistry with a custom configuration
//    CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("MICROSERVICES", circuitBreakerConfig);*/
//}
