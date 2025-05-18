package com.Podzilla.analytics.messaging.commands.inventory;

import com.Podzilla.analytics.messaging.commands.Command;
import com.Podzilla.analytics.services.OrderAnalyticsService;

import java.time.Instant;
import lombok.Builder;

@Builder
public class MarkOrderAsFailedToFulfillCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private String orderId;
    private String reason;
    private Instant timestamp;

    @Override
    public void execute() {
        orderAnalyticsService.markOrderAsFailedToFulfill(
            orderId,
            reason,
            timestamp
        );
    }

}
