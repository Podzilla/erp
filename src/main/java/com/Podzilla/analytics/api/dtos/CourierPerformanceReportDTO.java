package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierPerformanceReportDTO {
    private Long courierId;
    private String courierName;
    private Long deliveryCount;
    private BigDecimal successRate;
    private BigDecimal averageRating;

}
