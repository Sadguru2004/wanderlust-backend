package com.sadguru.wanderlust.controller;

import com.sadguru.wanderlust.entity.User;
import com.sadguru.wanderlust.security.JwtUtil;
import com.sadguru.wanderlust.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public User signup( @Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = userService.loginUser(user.getEmail(), user.getPassword());

        return jwtUtil.generateToken(existingUser.getEmail(),existingUser.getUsername());
    }
}
