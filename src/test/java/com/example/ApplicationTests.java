package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(RedisConfig.class)
public class ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Test
    public void contextLoads() throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            final int number = i;
            executorService.submit(() -> {
                final String key = String.valueOf(number);
                redisTemplate.opsForValue().set(key, number);
                logger.info("GET {}",redisTemplate.opsForValue().get(key));
                redisTemplate.delete(key);
            });
        }

        Thread.sleep(1000 * 10L);
    }

}
