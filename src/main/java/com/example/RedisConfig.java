package com.example;

import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    public static final int POOL_SIZE = 100;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.clusterNode("0.0.0.0", 7000);
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientResources(DefaultClientResources.builder().ioThreadPoolSize(POOL_SIZE).computationThreadPoolSize(POOL_SIZE).build())
                .build();
//        return new LettuceConnectionFactory(clusterConfiguration, clientConfig);
        return new LettuceConnectionFactory(clusterConfiguration);
    }

    @Bean
    RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
