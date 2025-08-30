package com.stelios.ServiceLink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_providers")
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This establishes a link where one ServiceProvider corresponds to one User.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private Integer age;

    private String profilePicUrl;

    // This will be an average rating, calculated and updated periodically.
    private double rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "provider_services",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services;
}