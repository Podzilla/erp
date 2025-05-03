package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierSuccessRateResponse {
    private Long courierId;
    private String courierName;
    private BigDecimal successRate;
}