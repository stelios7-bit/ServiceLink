package com.stelios.ServiceLink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {
    private Long id;
    private String fullName;
    private Integer age;
    private String profilePicUrl;
    private double rating;
}