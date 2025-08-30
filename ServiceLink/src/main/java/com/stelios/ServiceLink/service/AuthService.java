package com.stelios.ServiceLink.service;

import com.stelios.ServiceLink.dto.JwtAuthResponse;
import com.stelios.ServiceLink.dto.LoginRequest;
import com.stelios.ServiceLink.dto.SignUpRequest;
import com.stelios.ServiceLink.entity.Role;
import com.stelios.ServiceLink.entity.ServiceProvider;
import com.stelios.ServiceLink.entity.User;
import com.stelios.ServiceLink.repository.ServiceProviderRepository;
import com.stelios.ServiceLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void signup(SignUpRequest signUpRequest) {
        // Build user object
        User user = User.builder()
                .fullName(signUpRequest.getFullName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .role(Role.valueOf(signUpRequest.getRole()))
                .build();

        User savedUser = userRepository.save(user);

        // If the role is a provider, create a ServiceProvider entry
        if (user.getRole() == Role.ROLE_PROVIDER) {
            ServiceProvider provider = ServiceProvider.builder()
                    .user(savedUser)
                    .rating(0.0) // Initial rating
                    // You might want to get age and other details from the signup request too
                    .build();
            serviceProviderRepository.save(provider);
        }
    }

    public JwtAuthResponse login(LoginRequest loginRequest) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // If authentication is successful, generate a token
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        var jwt = jwtService.generateToken(user);

        return JwtAuthResponse.builder().accessToken(jwt).build();
    }
}