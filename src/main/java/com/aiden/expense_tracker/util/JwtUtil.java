package com.aiden.expense_tracker.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    // Secret key - used to sign and verify tokens
    private static final String SECRET = "mysupersecretkeythatishopefullylongenough1234567890"; // In production, use a secure key and store it safely
    private static final long EXPIRATION = 86400000; // 1 day in milliseconds

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate token for user
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // Check if token is valid
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // Token is invalid
            return false;
        }
    }
}
