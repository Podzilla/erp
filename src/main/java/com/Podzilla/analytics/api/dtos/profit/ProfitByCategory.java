package com.Podzilla.analytics.api.dtos.profit;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitByCategory {

    @Schema(description = "Product category name", example = "Electronics")
    private String category;

    @Schema(description = "Total revenue for the category in the given period",
            example = "15000.50")
    private BigDecimal totalRevenue;

    @Schema(description = "Total cost for the category in the given period",
            example = "10000.25")
    private BigDecimal totalCost;

    @Schema(description = "Gross profit (revenue - cost)",
            example = "5000.25")
    private BigDecimal grossProfit;

    @Schema(description = "Gross profit margin percentage",
            example = "33.33")
    private BigDecimal grossProfitMargin;
}
