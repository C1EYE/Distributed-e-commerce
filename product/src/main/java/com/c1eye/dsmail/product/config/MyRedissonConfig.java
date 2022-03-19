package com.c1eye.dsmail.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author c1eye
 * time 2022/3/17 14:53
 */
@Configuration
public class MyRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://81.68.107.235:6379");
        return Redisson.create(config);
    }
}
