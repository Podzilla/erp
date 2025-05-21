package com.Podzilla.analytics.messaging.commands.order;

import com.Podzilla.analytics.messaging.commands.Command;
import com.Podzilla.analytics.services.OrderAnalyticsService;
import lombok.Builder;

@Builder
public class AssignCourierToOrderCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private String orderId;
    private String courierId;

    @Override
    public void execute() {
        orderAnalyticsService.assignCourier(orderId, courierId);
    }
}
