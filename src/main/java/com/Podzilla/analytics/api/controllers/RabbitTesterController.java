package com.Podzilla.analytics.api.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        BaseEvent event = new CourierRegisteredEvent(
            "87f23fee-2e09-4331-bc9c-912045ef0832", "ahmad the courier", "010");
        listener.handleUserEvents(event);
    }

    @GetMapping("/customer-registered-event")
    public void testCustomerRegisteredEvent() {
        BaseEvent event = new CustomerRegisteredEvent(
            "27f7f5ca-6729-461e-882a-0c5123889bec",
            "7amada"
        );
        listener.handleUserEvents(event);
    }

    @GetMapping("/order-assigned-to-courier-event")
    public void testOrderAssignedToCourierEvent() {
        BaseEvent event = new OrderAssignedToCourierEvent(
            "e715d122-2628-4c68-82bc-a3c4fc1eefd1", "2");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-cancelled-event")
    public void testOrderCancelledEvent() {
        BaseEvent event = new OrderCancelledEvent(
            "d7c897d1-b23d-46aa-bfb6-258b4b8dcbd4", "2", "some reason");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivered-event")
    public void testOrderDeliveredEvent() {
        BaseEvent event = new OrderDeliveredEvent(
            "21063caa-4265-4286-8b16-6361b5bda83a", "2",
            new BigDecimal("10.0"));
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivery-failed-event")
    public void testOrderDeliveryFailedEvent() {
        BaseEvent event = new OrderDeliveryFailedEvent(
            "a0736362-6e37-46d7-95c6-4ad9092bf642", "2", "some reason");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-out-for-delivery-event")
    public void testOrderOutForDeliveryEvent() {
        BaseEvent event = new OrderOutForDeliveryEvent(
            "55399710-a835-4f66-ba9d-1d299e40702b", "2");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-placed-event")
    public void testOrderPlacedEvent() {
        BaseEvent event = new OrderPlacedEvent(
            "a1aa7c7d-fe6a-491f-a2cc-b3b923340777",
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
    public void testInventoryUpdatedEvent(
            @RequestParam final String productId,
            @RequestParam final Integer quantity
        ) {
        BaseEvent event = new InventoryUpdatedEvent(
            productId, quantity);
        listener.handleInventoryEvents(event);
    }

    @GetMapping("product-created-event")
    public void testProductCreatedEvent() {
        BaseEvent event = new ProductCreatedEvent(
            "f12afb47-ad23-4ca8-a162-8b12de7a5e49",
            "the rabbit product",
            "some category",
            new BigDecimal("10.0"),
            Integer.valueOf(1)
        );
        listener.handleInventoryEvents(event);
    }

}
