package com.Podzilla.analytics.messaging.commands.order;

import java.time.Instant;

import com.Podzilla.analytics.services.OrderAnalyticsService;
import com.Podzilla.analytics.messaging.commands.Command;

import lombok.Builder;

@Builder
public class CancelOrderCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private String orderId;
    private String reason;
    private Instant timestamp;

    @Override
    public void execute() {
        orderAnalyticsService.cancelOrder(
            orderId,
            reason,
            timestamp
        );
    }
}
