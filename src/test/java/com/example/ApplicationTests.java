package com.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(RedisConfig.class)
public class ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(200);

        for (int i = 0; i < 1000000; i++) {
            final int number = i;
            executorService.submit(() -> {
                final String key = String.valueOf(number);
                redisTemplate.opsForValue().set(key, RandomStringUtils.random(100));
//                logger.info("GET {}",redisTemplate.opsForValue().get(key));
                redisTemplate.delete(key);
            });
        }

        Thread.sleep(1000 * 20L);
    }

}
