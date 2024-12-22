package com.example.SynchronyAssessment;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.repository.UserRepository;
import com.example.SynchronyAssessment.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsersSequential() {
        List<User> mockUsers = Arrays.asList(
                new User(1L, "Alice", "alice@example.com"),
                new User(2L, "Bob", "bob@example.com")
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userService.getAllUsersSequential();
        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getName());
    }
}
