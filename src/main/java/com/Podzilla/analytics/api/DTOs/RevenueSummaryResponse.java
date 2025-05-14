package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueSummaryResponse {
    @Schema(description = "Start date of the period for the revenue summary", example = "2023-01-01")
    private LocalDate periodStartDate;

    @Schema(description = "Total revenue for the specified period", example = "12345.67")
    private BigDecimal totalRevenue;
}
