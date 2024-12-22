package com.example.SynchronyAssessment.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PerformanceLogger {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceLogger.class);

    private PerformanceLogger() {
    }

    public static <T> T measureExecutionTime(String taskName, Operation<T> operation) {
        long startTime = System.currentTimeMillis();
        try {
            return operation.execute();
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("{} took {} ms", taskName, (endTime - startTime));
        }
    }

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println(joinPoint.getSignature() + " executed in " + (end - start) + "ms");
        return result;
    }

    @FunctionalInterface
    public interface Operation<T> {
        T execute();
    }
}
