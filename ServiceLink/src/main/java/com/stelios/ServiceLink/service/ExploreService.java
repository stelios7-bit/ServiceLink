package com.stelios.ServiceLink.service;

import com.stelios.ServiceLink.dto.ProviderDto;
import com.stelios.ServiceLink.entity.Service;
import com.stelios.ServiceLink.exception.ResourceNotFoundException;
import com.stelios.ServiceLink.repository.ServiceProviderRepository;
import com.stelios.ServiceLink.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExploreService {

    private final ServiceProviderRepository providerRepository;
    private final ServiceRepository serviceRepository;

    /**
     * Finds providers associated with a specific service ID.
     * @param serviceId The ID of the service to filter by.
     * @return A list of Provider DTOs for the frontend.
     */
    public List<ProviderDto> getProvidersByService(Long serviceId) {
        // Step 1: Find the actual service entity from the database using its ID.
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));

        // Step 2: Use our new repository method to find all providers linked to that service.
        return providerRepository.findByServicesContaining(service)
                .stream()
                // Step 3: Convert the database entities into clean DTOs for the API response.
                .map(provider -> new ProviderDto(
                        provider.getId(),
                        provider.getUser().getFullName(),
                        provider.getAge(),
                        provider.getProfilePicUrl(),
                        provider.getRating()))
                .collect(Collectors.toList());
    }
}