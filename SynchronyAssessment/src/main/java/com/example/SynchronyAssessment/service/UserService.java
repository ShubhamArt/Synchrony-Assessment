package com.example.SynchronyAssessment.service;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

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

    private static final String USERS_CACHE_KEY = "users";

    @Async
    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> userRepository.findAll(), taskExecutor);
    }

    public User addUser(User user) {
        // Invalidate the cache when a user is added
        redisTemplate.delete(USERS_CACHE_KEY);
        return userRepository.save(user);
    }
}
