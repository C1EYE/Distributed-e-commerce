package com.c1eye.dsmail.product.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author c1eye
 * time 2022/3/20 18:41
 */
@Configuration
//@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class MyThreadConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties properties) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(properties.getCoreSize(),
                properties.getMaxSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10_0000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
