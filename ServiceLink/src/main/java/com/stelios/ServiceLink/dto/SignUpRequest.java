package com.stelios.ServiceLink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role; // Will be "ROLE_USER" or "ROLE_PROVIDER"
}