package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO containing courier success rate data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierSuccessRateResponse {

    /**
     * ID of the courier.
     */
    @Schema(description = "ID of the courier", example = "103")
    private Long courierId;

    /**
     * Full name of the courier.
     */
    @Schema(description = "Full name of the courier", example = "Fatima Ahmed")
    private String courierName;

    /**
     * Delivery success rate.
     */
    @Schema(description = "Delivery success rate", example = "0.87")
    private BigDecimal successRate;
}
