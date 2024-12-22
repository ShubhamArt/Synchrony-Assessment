package com.example.SynchronyAssessment;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class PerformanceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testParallelExecution() {
        long start = System.currentTimeMillis();
        CompletableFuture<List<User>> result = userService.getAllUsers();
        result.join();
        long end = System.currentTimeMillis();
        System.out.println("Parallel Execution Time: " + (end - start) + "ms");
    }
    @Test
    public void testSequentialExecution() {
        long start = System.currentTimeMillis();
        List<User> result = userService.getAllUsersSequential();
        long end = System.currentTimeMillis();
        System.out.println("Sequential Execution Time: " + (end - start) + "ms");
    }
}
