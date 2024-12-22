package com.example.SynchronyAssessment.controller;

import com.example.SynchronyAssessment.model.User;
import com.example.SynchronyAssessment.repository.UserRepository;
import com.example.SynchronyAssessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Users: " + users); // Debug log
        return users;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
