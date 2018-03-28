package com.mz.common.component;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis模板
 *
 * @author tongzhou
 * @date 2018-03-12 15:18
 **/
@Component
public class RedisCache {
    @Resource
    private RedisTemplate redisTemplate;

    public void setValue(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public void setValue(Object key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object getValue(Object key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }
}
