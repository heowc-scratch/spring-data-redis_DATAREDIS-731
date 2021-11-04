package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;

@Configuration
class RedisConfig {

    private static LettuceClientConfiguration lettuceClientConfiguration() {
        ClusterClientOptions clientOptions =
                ClusterClientOptions.builder()
                                    .topologyRefreshOptions(
                                            ClusterTopologyRefreshOptions.builder()
                                                                         .enableAllAdaptiveRefreshTriggers()
                                                                         .build())
                                    .build();
        return LettuceClientConfiguration.builder()
                                 .clientOptions(clientOptions)
                                 .build();
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.clusterNode("0.0.0.0", 7000);
        return new LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration());
    }

    @Bean
    RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
