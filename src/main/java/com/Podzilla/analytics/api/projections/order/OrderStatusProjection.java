package com.Podzilla.analytics.api.projections.order;
import com.Podzilla.analytics.models.Order.OrderStatus;

public interface OrderStatusProjection {
    OrderStatus getStatus();
    Long getCount();
}
