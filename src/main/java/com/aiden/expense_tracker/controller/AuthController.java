package com.aiden.expense_tracker.controller;

import com.aiden.expense_tracker.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Register request body
    record RegisterRequest(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password
    ) {}

    // Login request body
    record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
    ) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req.username(), req.email(), req.password());
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        String token = userService.login(req.username(), req.password());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
