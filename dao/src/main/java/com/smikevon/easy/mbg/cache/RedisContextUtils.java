package com.smikevon.easy.mbg.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.smikevon.easy.mbg.config.RedisConfiguration;

/**
 * Created by sean (smikevon@163.com) on 2018/8/30.
 */
@Component
public class RedisContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;
    private static volatile RedisTemplate<Object, Object> redisTemplate;

    public static RedisTemplate<Object, Object> getRedisTemplate() {
        if (redisTemplate == null) {
            synchronized(RedisContextUtils.class) {
                if (redisTemplate == null) {
                    redisTemplate = (RedisTemplate<Object, Object>) context.getBean("redisTemplate");
                    RedisSerializer<String> StringSerializer = new StringRedisSerializer();
                    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = RedisConfiguration
                            .getJackson2JsonSerializer();
                    redisTemplate.setKeySerializer(StringSerializer);
                    redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
                }
            }
        }
        return redisTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
