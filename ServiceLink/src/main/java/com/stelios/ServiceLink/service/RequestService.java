package com.stelios.ServiceLink.service;

import com.stelios.ServiceLink.dto.ServiceRequestDto;
import com.stelios.ServiceLink.dto.ServiceRequestResponseDto;
import com.stelios.ServiceLink.entity.*;
import com.stelios.ServiceLink.exception.ResourceNotFoundException;
import com.stelios.ServiceLink.repository.ServiceProviderRepository;
import com.stelios.ServiceLink.repository.ServiceRepository;
import com.stelios.ServiceLink.repository.ServiceRequestRepository;
import com.stelios.ServiceLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository providerRepository;
    private final ServiceRequestRepository requestRepository;

    /**
     * Creates a new service request for a logged-in customer.
     * @param requestDto The details of the request.
     * @param customerEmail The email of the authenticated customer.
     * @return A DTO of the newly created request.
     */
    public ServiceRequestResponseDto createRequest(ServiceRequestDto requestDto, String customerEmail) {
        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + customerEmail));

        ServiceProvider provider = providerRepository.findById(requestDto.getProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("ServiceProvider not found with id: " + requestDto.getProviderId()));

        com.stelios.ServiceLink.entity.Service service = serviceRepository.findById(requestDto.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + requestDto.getServiceId()));

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .customer(customer)
                .provider(provider)
                .service(service)
                .description(requestDto.getDescription())
                .address(requestDto.getAddress())
                .requestedSlot(requestDto.getRequestedSlot())
                .status(RequestStatus.PENDING)
                .build();

        ServiceRequest savedRequest = requestRepository.save(serviceRequest);
        return mapToDto(savedRequest);
    }

    /**
     * Cancels a service request.
     * Includes a critical ownership check to ensure the user owns the request.
     * @param requestId The ID of the request to cancel.
     * @param customerEmail The email of the authenticated customer.
     * @return A DTO of the cancelled request.
     */
    public ServiceRequestResponseDto cancelRequest(Long requestId, String customerEmail) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

        // Ownership check
        if (!request.getCustomer().getEmail().equals(customerEmail)) {
            throw new IllegalStateException("You are not authorized to cancel this request.");
        }

        request.setStatus(RequestStatus.CANCELLED);
        ServiceRequest savedRequest = requestRepository.save(request);
        return mapToDto(savedRequest);
    }

    /**
     * Updates an existing service request.
     * Includes a critical ownership check.
     * @param requestId The ID of the request to update.
     * @param requestDto The new details for the request.
     * @param customerEmail The email of the authenticated customer.
     * @return A DTO of the updated request.
     */
    public ServiceRequestResponseDto updateRequest(Long requestId, ServiceRequestDto requestDto, String customerEmail) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

        // Ownership check
        if (!request.getCustomer().getEmail().equals(customerEmail)) {
            throw new IllegalStateException("You are not authorized to update this request.");
        }

        // Update the fields
        request.setDescription(requestDto.getDescription());
        request.setAddress(requestDto.getAddress());
        request.setRequestedSlot(requestDto.getRequestedSlot());

        ServiceRequest savedRequest = requestRepository.save(request);
        return mapToDto(savedRequest);
    }

    /**
     * A private helper method to map a ServiceRequest entity to a ServiceRequestResponseDto.
     * @param request The entity to map.
     * @return The resulting DTO.
     */
    private ServiceRequestResponseDto mapToDto(ServiceRequest request) {
        return new ServiceRequestResponseDto(
                request.getId(),
                request.getCustomer().getFullName(),
                request.getProvider().getUser().getFullName(),
                request.getService().getName(),
                request.getDescription(),
                request.getAddress(),
                request.getRequestedSlot(),
                request.getStatus(),
                request.getOtp()
        );
    }
}