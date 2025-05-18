package com.Podzilla.analytics.messaging.commands.order;

import com.Podzilla.analytics.services.OrderAnalyticsService;
import com.Podzilla.analytics.messaging.commands.Command;
import java.time.Instant;

import lombok.Builder;

@Builder
public class MarkOrderAsOutForDeliveryCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private String orderId;
    private Instant timestamp;

    @Override
    public void execute() {
        orderAnalyticsService.markOrderAsOutForDelivery(orderId, timestamp);
    }

}
