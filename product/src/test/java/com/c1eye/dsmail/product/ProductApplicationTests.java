package com.c1eye.dsmail.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void redissonTest() {
        RLock lock = redissonClient.getLock("my-lock");
        lock.lock();
        try {
            System.out.println("加锁成功");
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }

    @Test
    public void redisTest() {
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String s = UUID.randomUUID().toString();
        stringStringValueOperations.set("hello", s);
        assert stringStringValueOperations.get("hello").equals(s);

    }
}
