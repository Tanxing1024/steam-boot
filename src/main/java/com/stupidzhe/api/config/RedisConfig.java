package com.stupidzhe.api.config;

import com.stupidzhe.api.domain.Bot;
import com.stupidzhe.api.domain.Confirmation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by Mr.W on 2017/5/25.
 * redis配置
 */
@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPassword("");
//        factory.setHostName("112.74.25.24");
        factory.setHostName("127.0.0.1");
        factory.setPort(6379);
        factory.setTimeout(5000);
        factory.setDatabase(1);
        factory.setUsePool(true);
        JedisPoolConfig config = factory.getPoolConfig();
        config.setMinIdle(1);
        config.setMaxWaitMillis(-1);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Bot> redisBotTemplate() {
        RedisTemplate<String, Bot> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, List<String>> redisListTemplate() {
        RedisTemplate<String, List<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, Confirmation> redisConfirmationTemplate() {
        RedisTemplate<String, Confirmation> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }


}
