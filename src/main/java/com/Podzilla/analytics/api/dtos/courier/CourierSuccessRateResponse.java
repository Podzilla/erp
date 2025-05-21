package com.Podzilla.analytics.api.dtos.courier;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierSuccessRateResponse {

    @Schema(description = "ID of the courier", example = "103")
    private UUID courierId;

    @Schema(description = "Full name of the courier", example = "Fatima Ahmed")
    private String courierName;

    @Schema(description = "Delivery success rate", example = "0.87")
    private BigDecimal successRate;
}
