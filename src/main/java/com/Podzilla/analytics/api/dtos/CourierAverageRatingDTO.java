package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierAverageRatingDTO {
    private Long courierId;
    private String courierName;
    private BigDecimal averageRating;
}