package com.example.SynchronyAssessment.service;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CacheService cacheService;
    private static final String USERS_CACHE_KEY = "users";

    @Async
    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> {
            List<User> cachedUsers = cacheService.getFromCache(USERS_CACHE_KEY, List.class);
            if (cachedUsers != null) {
                return cachedUsers;
            }
            List<User> users = userRepository.findAll();
            cacheService.putInCache(USERS_CACHE_KEY, users, 300); // Cache for 5 minutes
            return users;
        }, taskExecutor);
    }
    @Transactional(readOnly = true)
    public List<User> getAllUsersSequential() {
        return userRepository.findAll(); // Direct call without parallelism
    }
    public User addUser(User user) {
        // Invalidate the cache when a user is added
        User savedUser = userRepository.save(user);
        cacheService.evictFromCache(USERS_CACHE_KEY);
        return savedUser;
    }
}
