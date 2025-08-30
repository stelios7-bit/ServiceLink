package com.stelios.ServiceLink.dto;

import com.stelios.ServiceLink.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestResponseDto {
    private Long id;
    private String customerName;
    private String providerName;
    private String serviceName;
    private String description;
    private String address;
    private LocalDateTime requestedSlot;
    private RequestStatus status;
    private String otp; // Add this line
}