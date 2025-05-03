package com.Podzilla.analytics.api.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDeliveryCountDTO {
    private Long courierId;
    private String courierName;
    private Long deliveryCount;
}