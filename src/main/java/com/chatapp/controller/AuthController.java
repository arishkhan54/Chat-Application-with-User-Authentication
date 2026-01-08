package com.chatapp.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.model.User;
import com.chatapp.security.JwtUtil;
import com.chatapp.service.UserService;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;
    private final AuthenticationManager manager;
    private final JwtUtil jwt;

    public AuthController(UserService service, AuthenticationManager manager, JwtUtil jwt) {
        this.service = service;
        this.manager = manager;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (auth.isAuthenticated()) {
            return jwt.generateToken(user.getUsername());
        }

        return "Invalid Credentials!";
    }
}
