package com.eventhub.authservice.controller;

import com.eventhub.authservice.model.AuthRequest;
import com.eventhub.authservice.model.AuthResponse;
import com.eventhub.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}