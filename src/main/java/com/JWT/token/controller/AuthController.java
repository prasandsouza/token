package com.JWT.token.controller;

import com.JWT.token.model.AuthResponse;
import com.JWT.token.model.LoginRequest;
import com.JWT.token.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // End Point 1: Generate JWT
    @PostMapping("/generate")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody LoginRequest request) {
        String token = jwtUtil.generateToken(request.getUuid(), request.getUsername(), request.getEmail());

        return ResponseEntity.ok(new AuthResponse(true, "Token generated successfully", token));
    }

    // End Point 2: Read JWT and return success/failure with claims data
    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        // Check if the header is present and formatted correctly
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Missing or invalid Authorization header format", null));
        }

        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.extractToken(token);

            // Reconstruct a response body from the claims
            Map<String, Object> userData = Map.of(
                    "subject/username", claims.getSubject(),
                    "uuid", claims.get("uuid"),
                    "email", claims.get("email")
            );

            return ResponseEntity.ok(new AuthResponse(true, "Token is valid!", userData));

        } catch (JwtException | IllegalArgumentException e) {
            // Catches expired tokens, tampered signatures, etc.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Token validation failed: " + e.getMessage(), null));
        }
    }
}
