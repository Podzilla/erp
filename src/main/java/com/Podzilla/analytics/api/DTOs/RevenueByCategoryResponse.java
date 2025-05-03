package com.Podzilla.analytics.api.DTOs;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueByCategoryResponse {
    private String category;
    private BigDecimal totalRevenue; // Using camelCase for DTO fields is standard practice
}