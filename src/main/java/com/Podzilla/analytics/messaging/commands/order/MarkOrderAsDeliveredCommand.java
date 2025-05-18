package com.Podzilla.analytics.messaging.commands.order;

import java.math.BigDecimal;

import com.Podzilla.analytics.services.OrderAnalyticsService;

import lombok.Builder;

import com.Podzilla.analytics.messaging.commands.Command;
import java.time.Instant;

@Builder
public class MarkOrderAsDeliveredCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private String orderId;
    private BigDecimal courierRating;
    private Instant timestamp;

    @Override
    public void execute() {
        orderAnalyticsService.markOrderAsDelivered(
            orderId,
            courierRating,
            timestamp
        );
    }
}
