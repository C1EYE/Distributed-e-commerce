package com.c1eye.dsmail.product;

import com.c1eye.dsmail.product.dao.AttrGroupDao;
import com.c1eye.dsmail.product.dao.SkuSaleAttrValueDao;
import com.c1eye.dsmail.product.vo.SpuItemAttrGroupVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

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
        } catch (Exception e) {

        } finally {
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

    @Test
    public void future() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("1");
            return Thread.currentThread().getName();
        }, pool);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("2");
            return Thread.currentThread().getName();
        }, pool);

    }

    @Autowired
    AttrGroupDao attrGroupDao;

    @Test
    public void name() {
        List<SpuItemAttrGroupVo> list = attrGroupDao.getAttrGroupWithAttrsBySpuId(9L, 225L);
        System.out.println(list);
    }

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Test
    public void saltest() {
        System.out.println(skuSaleAttrValueDao.getSaleAttrsBySpuId(9L));
    }
}
