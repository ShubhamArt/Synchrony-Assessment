package com.example.SynchronyAssessment.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadingUtility {
    private static final Logger logger = LoggerFactory.getLogger(ThreadingUtility.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void executeTask(Runnable task) {
        executorService.submit(() -> {
            try {
                task.run();
            } catch (Exception e) {
                logger.error("Task execution failed: {}", e.getMessage());
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
        logger.info("Thread pool shutdown");
    }
}
