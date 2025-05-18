package com.Podzilla.analytics.messaging.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Podzilla.analytics.services.CustomerAnalyticsService;
import com.Podzilla.analytics.services.CourierAnalyticsService;
import com.Podzilla.analytics.services.ProductAnalyticsService;
import com.Podzilla.analytics.services.InventoryAnalyticsService;
import com.Podzilla.analytics.services.OrderAnalyticsService;
import com.Podzilla.analytics.services.RegionService;
import com.Podzilla.analytics.messaging.commands.user.RegisterCustomerCommand;
import com.Podzilla.analytics.messaging.commands.user.RegisterCourierCommand;
import com.Podzilla.analytics.messaging.commands.inventory.CreateProductCommand;
import com.Podzilla.analytics.messaging.commands.inventory.UpdateInventoryCommand;
import com.Podzilla.analytics.messaging.commands.inventory.MarkOrderAsFailedToFulfillCommand;
import com.Podzilla.analytics.messaging.commands.order.PlaceOrderCommand;
import com.Podzilla.analytics.messaging.commands.order.AssignCourierToOrderCommand;
import com.Podzilla.analytics.messaging.commands.order.CancelOrderCommand;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsOutForDeliveryCommand;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsDeliveredCommand;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsFailedToDeliverCommand;


import com.podzilla.mq.events.DeliveryAddress;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@Component
public class CommandFactory {

    @Autowired
    private CustomerAnalyticsService customerAnalyticsService;

    @Autowired
    private CourierAnalyticsService courierAnalyticsService;

    @Autowired
    private ProductAnalyticsService productAnalyticsService;

    @Autowired
    private InventoryAnalyticsService inventoryAnalyticsService;

    @Autowired
    private OrderAnalyticsService orderAnalyticsService;


    @Autowired
    private RegionService regionService;

    public RegisterCustomerCommand createRegisterCustomerCommand(
        final String customerId,
        final String customerName
    ) {
        return RegisterCustomerCommand.builder()
            .customerAnalyticsService(customerAnalyticsService)
            .customerId(customerId)
            .customerName(customerName)
            .build();
    }

    public RegisterCourierCommand createRegisterCourierCommand(
        final String courierId,
        final String courierName
    ) {
        return RegisterCourierCommand.builder()
            .courierAnalyticsService(courierAnalyticsService)
            .courierId(courierId)
            .courierName(courierName)
            .build();
    }

    public CreateProductCommand createCreateProductCommand(
        final String productId,
        final String productName,
        final String productCategory,
        final BigDecimal productCost,
        final Integer productLowStockThreshold
    ) {
        return CreateProductCommand.builder()
            .productAnalyticsService(productAnalyticsService)
            .productId(productId)
            .productName(productName)
            .productCategory(productCategory)
            .productCost(productCost)
            .productLowStockThreshold(productLowStockThreshold)
            .build();
    }

    public UpdateInventoryCommand createUpdateInventoryCommand(
        final String productId,
        final Integer quantity,
        final Instant timestamp
    ) {
        return UpdateInventoryCommand.builder()
            .inventoryAnalyticsService(inventoryAnalyticsService)
            .productId(productId)
            .quantity(quantity)
            .timestamp(timestamp)
            .build();
    }

    public PlaceOrderCommand createPlaceOrderCommand(
        final String orderId,
        final String customerId,
        final List<com.podzilla.mq.events.OrderItem> items,
        final DeliveryAddress deliveryAddress,
        final BigDecimal totalAmount,
        final Instant timestamp
    ) {
        return PlaceOrderCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .regionService(regionService)
            .orderId(orderId)
            .customerId(customerId)
            .items(items)
            .deliveryAddress(deliveryAddress)
            .totalAmount(totalAmount)
            .timestamp(timestamp)
            .build();
    }

    public CancelOrderCommand createCancelOrderCommand(
        final String orderId,
        final String reason,
        final Instant timestamp
    ) {
        return CancelOrderCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .reason(reason)
            .timestamp(timestamp)
            .build();
    }

    public AssignCourierToOrderCommand createAssignCourierToOrderCommand(
        final String orderId,
        final String courierId
    ) {
        return AssignCourierToOrderCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .courierId(courierId)
            .build();
    }

    public MarkOrderAsOutForDeliveryCommand
        createMarkOrderAsOutForDeliveryCommand(
        final String orderId,
        final Instant timestamp
    ) {
        return MarkOrderAsOutForDeliveryCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .timestamp(timestamp)
            .build();
    }

    public MarkOrderAsDeliveredCommand createMarkOrderAsDeliveredCommand(
        final String orderId,
        final BigDecimal courierRating,
        final Instant timestamp
    ) {
        return MarkOrderAsDeliveredCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .courierRating(courierRating)
            .timestamp(timestamp)
            .build();
    }

    public MarkOrderAsFailedToDeliverCommand
        createMarkOrderAsFailedToDeliverCommand(
        final String orderId,
        final String reason,
        final Instant timestamp
    ) {
        return MarkOrderAsFailedToDeliverCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .reason(reason)
            .timestamp(timestamp)
            .build();
    }

    public MarkOrderAsFailedToFulfillCommand
        createMarkOrderAsFailedToFulfillCommand(
        final String orderId,
        final String reason,
        final Instant timestamp
    ) {
        return MarkOrderAsFailedToFulfillCommand.builder()
            .orderAnalyticsService(orderAnalyticsService)
            .orderId(orderId)
            .reason(reason)
            .timestamp(timestamp)
            .build();
    }
}
