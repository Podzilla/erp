package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the average rating of a courier.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierAverageRatingResponse {

    /** ID of the courier. */
    @Schema(description = "ID of the courier", example = "101")
    private Long courierId;

    /** Full name of the courier. */
    @Schema(description = "Full name of the courier", example = "John Doe")
    private String courierName;

    /** Average rating of the courier. */
    @Schema(description = "Average rating of the courier", example = "4.6")
    private BigDecimal averageRating;
}
