package com.xzz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * 自定义 {@link RedisCacheConfiguration} 的序列化方式
 * 自定义 {@link RedisTemplate}，以及 key，value，hashKey，hashValue 的序列化方式。
 * hashKey，hashValue 存储的是 Long 类型或者 Integer类型，
 * 所以序列化方式可以选择 {@link GenericJackson2JsonRedisSerializer}
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * 自定义 RedisCacheConfiguration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
    }

    /**
     * 自定义 RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Serializable> serializableRedisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(genericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

}
