package com.stelios.ServiceLink.service;

import com.stelios.ServiceLink.dto.OtpVerificationRequest;
import com.stelios.ServiceLink.dto.ServiceRequestResponseDto;
import com.stelios.ServiceLink.entity.RequestStatus;
import com.stelios.ServiceLink.entity.ServiceRequest;
import com.stelios.ServiceLink.exception.ResourceNotFoundException;
import com.stelios.ServiceLink.repository.ServiceRequestRepository;
import com.stelios.ServiceLink.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ServiceRequestRepository requestRepository;

    /**
     * Fetches all service requests assigned to a specific provider.
     *
     * @param providerEmail The email of the authenticated provider.
     * @return A list of DTOs representing the service requests.
     */
    public List<ServiceRequestResponseDto> getRequestsForProvider(String providerEmail) {
        List<ServiceRequest> requests = requestRepository.findByProviderUserEmail(providerEmail);
        return requests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * A private helper method to map a ServiceRequest entity to a ServiceRequestResponseDto.
     *
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

    /**
     * Accepts a pending service request.
     * Includes a check to prevent scheduling conflicts.
     *
     * @param requestId The ID of the request to accept.
     * @return A DTO of the updated request with status ACCEPTED and a generated OTP.
     */
    public ServiceRequestResponseDto acceptRequest(Long requestId) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + requestId));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request cannot be accepted as it is not in PENDING state.");
        }

        // Slot overlap check: find other requests for this provider at the same time, excluding the current one.
        List<ServiceRequest> conflictingRequests = requestRepository.findByProviderIdAndRequestedSlotAndIdNot(
                request.getProvider().getId(), request.getRequestedSlot(), requestId);

        // If the list is not empty, a true conflict exists.
        if (!conflictingRequests.isEmpty()) {
            request.setStatus(RequestStatus.DENIED);
            requestRepository.save(request);
            throw new IllegalStateException("Time slot is not available. Request has been denied.");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        request.setOtp(OtpUtil.generateOtp());
        ServiceRequest savedRequest = requestRepository.save(request);
        return mapToDto(savedRequest);
    }

    /**
     * Denies a service request.
     *
     * @param requestId The ID of the request to deny.
     * @return A DTO of the updated request with status DENIED.
     */
    public ServiceRequestResponseDto denyRequest(Long requestId) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        request.setStatus(RequestStatus.DENIED);
        ServiceRequest savedRequest = requestRepository.save(request);
        return mapToDto(savedRequest);
    }

    /**
     * Completes a service request after verifying the OTP.
     *
     * @param requestId  The ID of the request to complete.
     * @param otpRequest A DTO containing the OTP from the user.
     * @return A DTO of the updated request with status COMPLETED.
     */
    public ServiceRequestResponseDto completeRequest(Long requestId, OtpVerificationRequest otpRequest) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if (request.getStatus() == RequestStatus.ACCEPTED && request.getOtp().equals(otpRequest.getOtp())) {
            request.setStatus(RequestStatus.COMPLETED);
            ServiceRequest savedRequest = requestRepository.save(request);
            return mapToDto(savedRequest);
        } else {
            throw new IllegalStateException("Invalid OTP or request cannot be completed.");
        }
    }
}