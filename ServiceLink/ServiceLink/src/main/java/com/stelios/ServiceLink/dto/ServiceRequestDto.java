package com.stelios.ServiceLink.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServiceRequestDto {
    private Long providerId;
    private Long serviceId;
    private String description;
    private String address;
    private LocalDateTime requestedSlot;
    // Note: We don't need customerId here because we'll get it from the logged-in user.
}