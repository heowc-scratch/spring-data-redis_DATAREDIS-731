package com.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final RedisTemplate<String, String> redisTemplate;

    public IndexController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public void index() {
        final String random = RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(random, random);
        logger.info("GET {}", redisTemplate.opsForValue().get(random));
        redisTemplate.delete(random);
    }
}
