package com.Podzilla.analytics.api.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.messaging.AnalyticsRabbitListener;
import com.podzilla.mq.events.BaseEvent;
import com.podzilla.mq.events.ConfirmationType;
import com.podzilla.mq.events.ProductSnapshot;
import com.podzilla.mq.events.WarehouseOrderFulfillmentFailedEvent;
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

import java.util.ArrayList;
@RestController
@RequestMapping("/rabbit-tester")
public class RabbitTesterController {

    static final int QUANTITY = 5;
    @Autowired
    private AnalyticsRabbitListener listener;

    @GetMapping("/courier-registered-event")
    public void testCourierRegisteredEvent() {
        BaseEvent event = new CourierRegisteredEvent(
                "87f23fee-2e09-4331-bc9c-912045ef0832",
                "ahmad the courier", "010");
        listener.handleUserEvents(event);
    }

    @GetMapping("/customer-registered-event")
    public void testCustomerRegisteredEvent() {
        BaseEvent event = new CustomerRegisteredEvent(
                "27f7f5ca-6729-461e-882a-0c5123889bec",
                "7amada");
        listener.handleUserEvents(event);
    }

    @GetMapping("/order-assigned-to-courier-event")
    public void testOrderAssignedToCourierEvent(
        @RequestParam final String orderId,
        @RequestParam final String courierId
    ) {
        BaseEvent event = new OrderAssignedToCourierEvent(
                orderId,
                courierId,
                new BigDecimal("10.0"), 0.0, 0.0, "signature",
                ConfirmationType.QR_CODE);
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-cancelled-event")
    public void testOrderCancelledEvent(
                @RequestParam final String orderId
    ) {
        BaseEvent event = new OrderCancelledEvent(
                orderId,
                "2", // customerId (not used in the event)
                "rabbit reason",
                new ArrayList<>()
        );
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivered-event")
    public void testOrderDeliveredEvent(
        @RequestParam final String orderId
    ) {
        BaseEvent event = new OrderDeliveredEvent(
                orderId, "2",
                new BigDecimal("4.73"));
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-delivery-failed-event")
    public void testOrderDeliveryFailedEvent(
        @RequestParam final String orderId
    ) {
        BaseEvent event = new OrderDeliveryFailedEvent(
                orderId, "the rabit delivery failed reason", "2");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-out-for-delivery-event")
    public void testOrderOutForDeliveryEvent(
        @RequestParam final String orderId
    ) {
        BaseEvent event = new OrderOutForDeliveryEvent(
                orderId, "2");
        listener.handleOrderEvents(event);
    }
    @GetMapping("/order-fulfillment-failed-event")
    public void testOrderFailedToFulfill(
        @RequestParam final String orderId
    ) {
        BaseEvent event = new WarehouseOrderFulfillmentFailedEvent(
                orderId, "order fulfillment failed rabbit reason");
        listener.handleOrderEvents(event);
    }

    @GetMapping("/order-placed-event")
    public void testOrderPlacedEvent(
        @RequestParam final String customerId,
        @RequestParam final String productId1,
        @RequestParam final String productId2
) {
        BaseEvent event = new OrderPlacedEvent(
                "a1aa7c7d-fe6a-491f-a2cc-b3b923340777",
                customerId,
                Arrays.asList(
                        new com.podzilla.mq.events.OrderItem(productId1,
                                QUANTITY, new BigDecimal("8.5")),
                        new com.podzilla.mq.events.OrderItem(productId2,
                                QUANTITY, new BigDecimal("12.75"))
                ),
                new DeliveryAddress(
                        "rabbit street",
                        "rabbit city wallahy",
                        "some state",
                        "some country",
                        "some postal code"),
                new BigDecimal("13290.0"), 0.0, 0.0, "signature",
                ConfirmationType.QR_CODE);
        listener.handleOrderEvents(event);
    }

    @GetMapping("inventory-updated-event")
    public void testInventoryUpdatedEvent(
            @RequestParam final String productId,
            @RequestParam final Integer quantity) {
        BaseEvent event = new InventoryUpdatedEvent(
                List.of(new ProductSnapshot(productId, quantity)));
        listener.handleInventoryEvents(event);
    }

    @GetMapping("product-created-event")
    public void testProductCreatedEvent() {
        BaseEvent event = new ProductCreatedEvent(
                "f12afb47-ad23-4ca8-a162-8b12de7a5e49",
                "the rabbit product",
                "some category",
                new BigDecimal("10.0"),
                Integer.valueOf(1));
        listener.handleInventoryEvents(event);
    }

}
