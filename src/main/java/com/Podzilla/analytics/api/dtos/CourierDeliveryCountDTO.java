package com.Podzilla.analytics.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDeliveryCountDTO {
    private Long courierId;
    private String courierName;
    private Long deliveryCount;
}
