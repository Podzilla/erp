package com.Podzilla.analytics.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Podzilla.analytics.messaging.invokers.user.CourierRegisteredInvoker;
import com.Podzilla.analytics.messaging.invokers.user.CustomerRegisteredInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderAssignedToCourierInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderDeliveryFailedInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderPlacedInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderCancelledInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderDeliveredInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderOutForDeliveryInvoker;
import com.Podzilla.analytics.messaging.invokers.InvokerFactory;
import com.Podzilla.analytics.messaging.invokers.inventory.InventoryUpdatedInvoker;
import com.Podzilla.analytics.messaging.invokers.inventory.ProductCreatedInvoker;
import com.Podzilla.analytics.messaging.invokers.inventory.OrderFulfillmentFailedInvoker;

import com.podzilla.mq.events.CourierRegisteredEvent;
import com.podzilla.mq.events.CustomerRegisteredEvent;
import com.podzilla.mq.events.OrderAssignedToCourierEvent;
import com.podzilla.mq.events.OrderDeliveryFailedEvent;
import com.podzilla.mq.events.OrderPlacedEvent;
import com.podzilla.mq.events.OrderCancelledEvent;
import com.podzilla.mq.events.OrderDeliveredEvent;
import com.podzilla.mq.events.OrderOutForDeliveryEvent;
import com.podzilla.mq.events.InventoryUpdatedEvent;
import com.podzilla.mq.events.ProductCreatedEvent;
import com.podzilla.mq.events.WarehouseOrderFulfillmentFailedEvent;


@Configuration
public class InvokerDispatcherConfig {

    @Autowired
    private final InvokerFactory invokerFactory;

    public InvokerDispatcherConfig(final InvokerFactory invokerFactory) {
        this.invokerFactory = invokerFactory;
    }

    @Bean
    public InvokerDispatcher invokerDispatcher() {
        InvokerDispatcher dispatcher = new InvokerDispatcher();

        registerUserInvokers(dispatcher);
        registerOrderInvokers(dispatcher);
        registerInventoryInvokers(dispatcher);

        return dispatcher;
    }

    private void registerUserInvokers(
        final InvokerDispatcher dispatcher
    ) {
        dispatcher.registerInvoker(
            CourierRegisteredEvent.class,
            invokerFactory.createInvoker(CourierRegisteredInvoker.class)
        );

        dispatcher.registerInvoker(
            CustomerRegisteredEvent.class,
            invokerFactory.createInvoker(CustomerRegisteredInvoker.class)
        );
    }

    private void registerOrderInvokers(
        final InvokerDispatcher dispatcher
    ) {
        dispatcher.registerInvoker(
            OrderAssignedToCourierEvent.class,
            invokerFactory.createInvoker(OrderAssignedToCourierInvoker.class)
        );
        dispatcher.registerInvoker(
            OrderCancelledEvent.class,
            invokerFactory.createInvoker(OrderCancelledInvoker.class)
        );
        dispatcher.registerInvoker(
            OrderDeliveredEvent.class,
            invokerFactory.createInvoker(OrderDeliveredInvoker.class)
        );
        dispatcher.registerInvoker(
            OrderDeliveryFailedEvent.class,
            invokerFactory.createInvoker(OrderDeliveryFailedInvoker.class)
        );
        dispatcher.registerInvoker(
            OrderOutForDeliveryEvent.class,
            invokerFactory.createInvoker(OrderOutForDeliveryInvoker.class)
        );
        dispatcher.registerInvoker(
            OrderPlacedEvent.class,
            invokerFactory.createInvoker(OrderPlacedInvoker.class)
        );
    }

    private void registerInventoryInvokers(
        final InvokerDispatcher dispatcher
    ) {
        dispatcher.registerInvoker(
            InventoryUpdatedEvent.class,
            invokerFactory.createInvoker(InventoryUpdatedInvoker.class)
        );
        dispatcher.registerInvoker(
            ProductCreatedEvent.class,
            invokerFactory.createInvoker(ProductCreatedInvoker.class)
        );

        dispatcher.registerInvoker(
            WarehouseOrderFulfillmentFailedEvent.class,
            invokerFactory.createInvoker(OrderFulfillmentFailedInvoker.class)
        );
    }

}
