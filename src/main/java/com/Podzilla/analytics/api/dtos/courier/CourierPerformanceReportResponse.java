package com.Podzilla.analytics.api.dtos.courier;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierPerformanceReportResponse {

    @Schema(description = "ID of the courier", example = "105")
    private UUID courierId;

    @Schema(description = "Full name of the courier", example = "Ali Hassan")
    private String courierName;

    @Schema(description = "Total number of deliveries", example = "87")
    private Long deliveryCount;

    @Schema(
        description = "Success rate as a decimal value (e.g., 0.92 for 92%)",
        example = "0.92"
    )
    private BigDecimal successRate;

    @Schema(description = "Average customer rating", example = "4.8")
    private BigDecimal averageRating;
}
