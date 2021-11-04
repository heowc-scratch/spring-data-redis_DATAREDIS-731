package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Import(RedisConfig.class)
class ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() throws Exception {
//        final ExecutorService executorService = Executors.newFixedThreadPool(200);

        for (int i = 0; i < 1_000_000; i++) {
            final int number = i;
//            executorService.submit(() -> {
                final String key = String.valueOf(number);
                redisTemplate.opsForValue().set(key, RandomStringUtils.random(10));
                logger.info("GET {}",redisTemplate.opsForValue().get(key));
                redisTemplate.delete(key);

                Thread.sleep(1_000L);
//            });
        }

        Thread.sleep(1000 * 20L);
    }

}
