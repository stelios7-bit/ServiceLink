package com.stelios.ServiceLink.controller;

import com.stelios.ServiceLink.dto.OtpVerificationRequest;
import com.stelios.ServiceLink.dto.ServiceRequestResponseDto;
import com.stelios.ServiceLink.entity.ServiceRequest;
import com.stelios.ServiceLink.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping("/requests")
    public ResponseEntity<List<ServiceRequestResponseDto>> getProviderRequests(Principal principal) {
        List<ServiceRequestResponseDto> requests = providerService.getRequestsForProvider(principal.getName());
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/requests/{requestId}/accept")
    public ResponseEntity<ServiceRequestResponseDto> acceptRequest(@PathVariable Long requestId) {
        ServiceRequestResponseDto updatedRequest = providerService.acceptRequest(requestId);
        return ResponseEntity.ok(updatedRequest);
    }

    @PostMapping("/requests/{requestId}/deny")
    public ResponseEntity<ServiceRequestResponseDto> denyRequest(@PathVariable Long requestId) {
        ServiceRequestResponseDto updatedRequest = providerService.denyRequest(requestId);
        return ResponseEntity.ok(updatedRequest);
    }

    @PostMapping("/requests/{requestId}/complete")
    public ResponseEntity<ServiceRequestResponseDto> completeRequest(@PathVariable Long requestId, @RequestBody OtpVerificationRequest otpRequest) {
        ServiceRequestResponseDto completedRequest = providerService.completeRequest(requestId, otpRequest);
        return ResponseEntity.ok(completedRequest);
    }
    // In ProviderService.java

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
                request.getOtp() // Add this line
        );
    }
}