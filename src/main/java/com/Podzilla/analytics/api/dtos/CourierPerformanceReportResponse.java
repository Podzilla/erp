package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO containing detailed courier performance metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierPerformanceReportResponse {

    /**
     * ID of the courier.
     */
    @Schema(description = "ID of the courier", example = "105")
    private Long courierId;

    /**
     * Full name of the courier.
     */
    @Schema(description = "Full name of the courier", example = "Ali Hassan")
    private String courierName;

    /**
     * Total number of deliveries completed by the courier.
     */
    @Schema(description = "Total number of deliveries", example = "87")
    private Long deliveryCount;

    /**
     * Success rate as a decimal value (e.g., 0.92 for 92%).
     */
    @Schema(
        description = "Success rate as a decimal value (e.g., 0.92 for 92%)",
        example = "0.92"
    )
    private BigDecimal successRate;

    /**
     * Average customer rating for the courier.
     */
    @Schema(description = "Average customer rating", example = "4.8")
    private BigDecimal averageRating;
}
