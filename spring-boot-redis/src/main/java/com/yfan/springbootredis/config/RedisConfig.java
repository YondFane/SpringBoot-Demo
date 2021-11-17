package com.yfan.springbootredis.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

// Redis配置
@Configuration
@EnableCaching//启用缓存 开启基于注解的缓存
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    /*
     * 默认模板只支持RedisTemplate，只能存入字符串
     * @author YFAN
     * @date 2021/11/17/017
     * @param  * @param redisConnectionFactory
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.io.Serializable>
     */
//    @Bean
//    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
//        // 设置模板使用的KEY序列化器
//        template.setKeySerializer(new StringRedisSerializer());
//        // 设置模板使用的VALUE序列化器
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }
//
//    /*
//     * 缓存配置，默认支持序列化和反序列化
//     * 加上此配置则为Json形式
//     * @author YFAN
//     * @date 2021/11/17/017
//     * @param  * @param connectionFactory
//     * @return org.springframework.cache.CacheManager
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//        return RedisCacheManager.builder(connectionFactory).cacheDefaults(redisCacheConfiguration).build();
//    }
}
