package com.example.SynchronyAssessment;

import com.example.SynchronyAssessment.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private CacheService cacheService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testPutInCache() {
        String key = "testKey";
        String value = "testValue";
        long ttl = 300;

        // Test with TTL
        cacheService.putInCache(key, value, ttl);
        verify(valueOperations, times(1)).set(key, value, ttl, TimeUnit.SECONDS);

        // Test without TTL
        cacheService.putInCache(key, value, 0);
        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    void testGetFromCache() {
        String key = "testKey";
        String value = "testValue";

        when(valueOperations.get(key)).thenReturn(value);

        String cachedValue = cacheService.getFromCache(key, String.class);
        assertEquals(value, cachedValue);
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    void testEvictFromCache() {
        String key = "testKey";

        cacheService.evictFromCache(key);
        verify(redisTemplate, times(1)).delete(key);
    }

    @Test
    void testCacheMiss() {
        String key = "missingKey";

        when(valueOperations.get(key)).thenReturn(null);

        String cachedValue = cacheService.getFromCache(key, String.class);
        assertNull(cachedValue);
        verify(valueOperations, times(1)).get(key);
    }
}
