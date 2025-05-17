package com.Podzilla.analytics.api.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.messaging.AnalyticsRabbitListener;
import com.podzilla.mq.events.BaseEvent;
import com.podzilla.mq.events.CourierRegisteredEvent;
import com.podzilla.mq.events.CustomerRegisteredEvent;
import com.podzilla.mq.events.DeliveryAddress;
import com.podzilla.mq.events.InventoryUpdatedEvent;
import com.podzilla.mq.events.OrderAssignedToCourierEvent;
import com.podzilla.mq.events.OrderCancelledEvent;
import com.podzilla.mq.events.OrderDeliveredEvent;
import com.podzilla.mq.events.OrderDeliveryFailedEvent;
import com.podzilla.mq.events.OrderOutForDeliveryEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.mq.events.ProductCreatedEvent;

@RestController
@RequestMapping("/rabbit-tester")
public class RabbitTesterController {

    @Autowired
    private AnalyticsRabbitListener listener;

    @GetMapping("/courier-registered-event")
    public void testCourierRegisteredEvent() {
        BaseEvent event = new CourierRegisteredEvent("1", "ahmad", "010");
        listener.handleUserEvents(event);
    }

    @GetMapping("/customer-registered-event")
    public void testCustomerRegisteredEvent() {
        BaseEvent event = new CustomerRegisteredEvent("1", "ahmad");
        listener.handleUserEvents(event);
    }

    @GetMapping("/order-assigned-to-courier-event")
    public void testOrderAssignedToCourierEvent() {
        BaseEvent event = new OrderAssignedToCourierEvent("1", "2");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-cancelled-event")
    public void testOrderCancelledEvent() {
        BaseEvent event = new OrderCancelledEvent("1", "2", "some reason");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivered-event")
    public void testOrderDeliveredEvent() {
        BaseEvent event = new OrderDeliveredEvent("1", "2",
            new BigDecimal("10.0"));
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivery-failed-event")
    public void testOrderDeliveryFailedEvent() {
        BaseEvent event = new OrderDeliveryFailedEvent("1", "2", "some reason");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-out-for-delivery-event")
    public void testOrderOutForDeliveryEvent() {
        BaseEvent event = new OrderOutForDeliveryEvent("1", "2");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-placed-event")
    public void testOrderPlacedEvent() {
        BaseEvent event = new OrderPlacedEvent(
            "1",
            "2",
            new BigDecimal("10.0"),
            new ArrayList<>(),
            new DeliveryAddress(
                "some street",
                "some city",
                "some state",
                "some country",
                "some postal code"
            )
        );
        listener.handleOrderEvents(event);
    }

    @GetMapping("inventory-updated-event")
    public void testInventoryUpdatedEvent() {
        BaseEvent event = new InventoryUpdatedEvent("1", Integer.valueOf(1));
        listener.handleInventoryEvents(event);
    }

    @GetMapping("product-created-event")
    public void testProductCreatedEvent() {
        BaseEvent event = new ProductCreatedEvent(
            "1",
            "some name",
            "some category",
            new BigDecimal("10.0"),
            Integer.valueOf(1)
        );
        listener.handleInventoryEvents(event);
    }

}
