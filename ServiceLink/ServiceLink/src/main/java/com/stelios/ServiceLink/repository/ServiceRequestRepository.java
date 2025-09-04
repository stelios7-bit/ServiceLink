package com.stelios.ServiceLink.repository;

import com.stelios.ServiceLink.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List; // Make sure to import List
import java.util.Optional;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    // This new method finds all requests assigned to a provider via their user email
    List<ServiceRequest> findByProviderUserEmail(String email);
    List<ServiceRequest> findByProviderIdAndRequestedSlotAndIdNot(Long providerId, LocalDateTime requestedSlot, Long requestId);
}