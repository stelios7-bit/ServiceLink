package com.stelios.ServiceLink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many requests can be made by one customer.
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // Many requests can be assigned to one provider.
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider provider;

    // Many requests can be for the same type of service.
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    private String description;

    private String address;

    private LocalDateTime requestedSlot;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private String otp;

    // One request can have multiple images.
    // 'cascade = CascadeType.ALL' means if we delete a request, its images are also deleted.
    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestImage> images;
}