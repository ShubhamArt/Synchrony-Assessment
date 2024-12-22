package com.example.SynchronyAssessment;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.repository.UserRepository;
import com.example.SynchronyAssessment.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;
import java.util.concurrent.CompletableFuture;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = List.of(new User(1L, "Alice", "alice@example.com"));
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        CompletableFuture<List<User>> users = userService.getAllUsers();
        Assertions.assertEquals(1, users.join().size());
    }
}
