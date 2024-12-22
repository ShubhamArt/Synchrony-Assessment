package com.example.SynchronyAssessment.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class CacheUtility {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtility.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheUtility(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToCache(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
        logger.info("Saved key '{}' to cache with timeout {} {}", key, timeout, unit);
    }

    public Object getFromCache(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        logger.info(value != null ? "Cache hit for '{}'" : "Cache miss for '{}'", key);
        return value;
    }

    public void removeFromCache(String key) {
        redisTemplate.delete(key);
        logger.info("Removed key '{}' from cache", key);
    }
}
