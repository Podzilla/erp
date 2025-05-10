package com.Podzilla.analytics.api.dtos;

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
    private String productName;
    private String category;
    private BigDecimal value;
}