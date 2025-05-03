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
public class TopSellerResponse {
    private Long productId;
    private String productName; // Assuming Product entity has a 'name' field
    private String category;    // Assuming Product entity has a 'category' field
    private BigDecimal value;   // This will hold either total revenue or total units, use BigDecimal for flexibility
}