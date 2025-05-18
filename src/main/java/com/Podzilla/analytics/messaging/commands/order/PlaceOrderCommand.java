package com.Podzilla.analytics.messaging.commands.order;

import java.math.BigDecimal;

import com.Podzilla.analytics.services.OrderAnalyticsService;
import com.Podzilla.analytics.services.RegionService;
import com.podzilla.mq.events.DeliveryAddress;
import com.Podzilla.analytics.messaging.commands.Command;
import com.Podzilla.analytics.models.Region;

import java.util.List;
import java.time.Instant;
import lombok.Builder;

@Builder
public class PlaceOrderCommand implements Command {
    private OrderAnalyticsService orderAnalyticsService;
    private RegionService regionService;
    private String orderId;
    private String customerId;
    private List<com.podzilla.mq.events.OrderItem> items;
    private DeliveryAddress deliveryAddress;
    private BigDecimal totalAmount;
    private Instant timestamp;

    @Override
    public void execute() {
        System.out.println("Executing PlaceOrderCommand henaaa");
        Region region = regionService.saveRegion(
            deliveryAddress.getCity(),
            deliveryAddress.getState(),
            deliveryAddress.getCountry(),
            deliveryAddress.getPostalCode()
        );
        orderAnalyticsService.saveOrder(
            orderId,
            customerId,
            items,
            region,
            totalAmount,
            timestamp
        );
    }
}
