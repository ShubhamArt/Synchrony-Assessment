package com.example.SynchronyAssessment.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.web.client.ResourceAccessException;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Retrieve an object from the cache.
     *
     * @param key the key to look up in the cache.
     * @param clazz the type of the cached value.
     * @param <T> the type parameter for the value.
     * @return the cached object, or null if not found.
     */
    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public <T> T getFromCache(String key, Class<T> clazz) {
        Object cachedObject = redisTemplate.opsForValue().get(key);
        return clazz.cast(cachedObject);
    }

    /**
     * Add or update an object in the cache with an optional time-to-live.
     *
     * @param key the key to store the object.
     * @param value the value to cache.
     * @param ttlInSeconds time-to-live in seconds (optional, 0 means no expiration).
     */
    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void putInCache(String key, Object value, long ttlInSeconds) {
        if (ttlInSeconds > 0) {
            redisTemplate.opsForValue().set(key, value, ttlInSeconds, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * Remove an object from the cache.
     *
     * @param key the key to remove.
     */
    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void evictFromCache(String key) {
        redisTemplate.delete(key);
    }

}
