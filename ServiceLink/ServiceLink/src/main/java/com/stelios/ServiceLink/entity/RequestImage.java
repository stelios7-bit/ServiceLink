package com.stelios.ServiceLink.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request_images")
public class RequestImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private ServiceRequest serviceRequest;
}