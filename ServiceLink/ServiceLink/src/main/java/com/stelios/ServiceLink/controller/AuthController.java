package com.stelios.ServiceLink.controller;

import com.stelios.ServiceLink.dto.JwtAuthResponse;
import com.stelios.ServiceLink.dto.LoginRequest;
import com.stelios.ServiceLink.dto.SignUpRequest;
import com.stelios.ServiceLink.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint to register a new user or service provider.
     * @param signUpRequest The details for the new account.
     * @return A success message.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        authService.signup(signUpRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Endpoint to authenticate a user and receive a JWT.
     * @param loginRequest The user's login credentials.
     * @return A JWT authentication response.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtAuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}