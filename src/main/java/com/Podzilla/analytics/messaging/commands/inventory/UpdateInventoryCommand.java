package com.Podzilla.analytics.messaging.commands.inventory;

import com.Podzilla.analytics.services.InventoryAnalyticsService;

import lombok.Builder;

import com.Podzilla.analytics.messaging.commands.Command;
import java.time.Instant;

@Builder
public class UpdateInventoryCommand implements Command {
    private final InventoryAnalyticsService inventoryAnalyticsService;
    private final String productId;
    private final Integer quantity;
    private final Instant timestamp;

    @Override
    public void execute() {
        inventoryAnalyticsService.saveInventorySnapshot(
            productId,
            quantity,
            timestamp
        );
    }
}
