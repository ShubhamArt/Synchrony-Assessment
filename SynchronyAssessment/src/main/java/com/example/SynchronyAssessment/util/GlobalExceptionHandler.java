package com.example.SynchronyAssessment.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.web.client.ResourceAccessException;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle database connection failures
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        logger.error("Database error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Database connection error. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Handle Redis connection failures
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleRedisException(ResourceAccessException ex) {
        logger.error("Redis error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Redis connection error. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Handle timeout exceptions
    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<String> handleTimeoutException(TimeoutException ex) {
        logger.error("Operation timed out: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Request timeout. Please try again later.", HttpStatus.REQUEST_TIMEOUT);
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
