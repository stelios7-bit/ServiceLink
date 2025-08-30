package com.stelios.ServiceLink.controller;

import com.stelios.ServiceLink.dto.ServiceRequestDto;
import com.stelios.ServiceLink.dto.ServiceRequestResponseDto;
import com.stelios.ServiceLink.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ServiceRequestResponseDto> createRequest(@RequestBody ServiceRequestDto requestDto, Principal principal) {
        ServiceRequestResponseDto createdRequest = requestService.createRequest(requestDto, principal.getName());
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<ServiceRequestResponseDto> updateRequest(@PathVariable Long id, @RequestBody ServiceRequestDto requestDto, Principal principal) {
        // CHANGE 2: Update the variable type here
        ServiceRequestResponseDto updatedRequest = requestService.updateRequest(id, requestDto, principal.getName());
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelRequest(@PathVariable Long id, Principal principal) {
        requestService.cancelRequest(id, principal.getName());
        return ResponseEntity.ok("Request cancelled successfully.");
    }
}