package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierSuccessRateResponse {

    @Schema(description = "ID of the courier", example = "103")
    private Long courierId;

    @Schema(description = "Full name of the courier", example = "Fatima Ahmed")
    private String courierName;

    @Schema(description = "Delivery success rate", example = "0.87")
    private BigDecimal successRate;
}
