package com.stelios.ServiceLink.controller;

import com.stelios.ServiceLink.dto.ProviderDto;
import com.stelios.ServiceLink.entity.Service;
import com.stelios.ServiceLink.repository.ServiceProviderRepository;
import com.stelios.ServiceLink.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository providerRepository;

    @GetMapping("/services")
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/providers")
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        // We convert the ServiceProvider entity to a ProviderDto to avoid exposing sensitive user data
        List<ProviderDto> providers = providerRepository.findAll().stream()
                .map(provider -> new ProviderDto(
                        provider.getId(),
                        provider.getUser().getFullName(),
                        provider.getAge(),
                        provider.getProfilePicUrl(),
                        provider.getRating()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(providers);
    }
}