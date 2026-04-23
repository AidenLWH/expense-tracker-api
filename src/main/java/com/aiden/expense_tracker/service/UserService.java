package com.aiden.expense_tracker.service;

import com.aiden.expense_tracker.model.User;
import com.aiden.expense_tracker.repository.UserRepository;
import com.aiden.expense_tracker.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder; // Remember this for work
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypting
        String hashedPassword = passwordEncoder.encode(password);
        return userRepository.save(new User(username, hashedPassword, email));
    }
    
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
        
    }
}
