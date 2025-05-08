package com.Podzilla.analytics.api.dtos.courier;

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
public class CourierAverageRatingResponse {

    @Schema(description = "ID of the courier", example = "101")
    private Long courierId;

    @Schema(description = "Full name of the courier", example = "John Doe")
    private String courierName;

    @Schema(description = "Average rating of the courier", example = "4.6")
    private BigDecimal averageRating;
}
