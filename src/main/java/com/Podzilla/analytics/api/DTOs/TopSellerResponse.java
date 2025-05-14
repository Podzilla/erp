package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellerResponse {
    @Schema(description = "Product ID", example = "101")
    private Long productId;
    @Schema(description = "Product name", example = "Wireless Mouse")
    private String productName;
    @Schema(description = "Product category", example = "Electronics")
    private String category;
    @Schema(description = "Total value sold", example = "2500.75")
    private BigDecimal value;
}