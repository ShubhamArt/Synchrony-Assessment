package com.example.SynchronyAssessment.controller;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsersSequential();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch users: " + e.getMessage(), e);
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        try {
            return userService.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
        }
    }
}
