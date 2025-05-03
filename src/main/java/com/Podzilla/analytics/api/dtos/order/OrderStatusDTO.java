package com.Podzilla.analytics.api.dtos.order;

import com.Podzilla.analytics.models.Order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {
    private OrderStatus status;
    private Long count;
}
