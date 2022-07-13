package org.hantiv.zfaka.config;

import org.hantiv.zfaka.serializer.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author Zhikun Han
 * @Date Created in 16:49 2022/7/13
 * @Description:
 */
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        //set key serializer
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        //set value serializer
        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
