package com.chatapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.security.JwtUtil;

@RestController
public class DebugController {

    private final JwtUtil jwtUtil;

    public DebugController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/debug")
    public String debug(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        return jwtUtil.extractUsername(token);
    }
}
