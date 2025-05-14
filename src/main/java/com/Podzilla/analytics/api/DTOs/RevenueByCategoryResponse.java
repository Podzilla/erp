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
public class RevenueByCategoryResponse {
    @Schema(description = "Category name", example = "Electronics")
    private String category;
    @Schema(description = "Total revenue for the category", example = "12345.67")
    private BigDecimal totalRevenue;
}