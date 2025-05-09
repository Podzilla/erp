package com.Podzilla.analytics.api.dtos.order;

import com.Podzilla.analytics.models.Order.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {

    @Schema(description = "Order status", example = "COMPLETED")
    private OrderStatus status;

    @Schema(description = "Count of orders with this status", example = "150")
    private Long count;
}
