package com.Podzilla.analytics.messaging.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Podzilla.analytics.services.CustomerAnalyticsService;
import com.Podzilla.analytics.services.CourierAnalyticsService;
import com.Podzilla.analytics.services.ProductAnalyticsService;
import com.Podzilla.analytics.services.InventoryAnalyticsService;
import com.Podzilla.analytics.messaging.commands.user.RegisterCustomerCommand;
import com.Podzilla.analytics.messaging.commands.user.RegisterCourierCommand;
import com.Podzilla.analytics.messaging.commands.inventory.CreateProductCommand;
import com.Podzilla.analytics.messaging.commands.inventory.UpdateInventoryCommand;

import java.math.BigDecimal;
import java.time.Instant;
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
}
