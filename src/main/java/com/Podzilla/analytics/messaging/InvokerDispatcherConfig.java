package com.Podzilla.analytics.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Podzilla.analytics.messaging.invokers.user.CourierRegisteredInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderAssignedToCourierInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderDeliveryFailedInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderPlacedInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderCancelledInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderDeliveredInvoker;
import com.Podzilla.analytics.messaging.invokers.order.OrderOutForDeliveryInvoker;
import com.Podzilla.analytics.messaging.invokers.InvokerFactory;
import com.Podzilla.analytics.messaging.invokers.inventory.InventoryUpdatedInvoker;
import com.Podzilla.analytics.messaging.invokers.inventory.ProductCreatedInvoker;

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
            new CourierRegisteredInvoker()
        );

        dispatcher.registerInvoker(
            CustomerRegisteredEvent.class,
            invokerFactory.createRegisterCustomerInvoker()
        );
    }

    private void registerOrderInvokers(
        final InvokerDispatcher dispatcher
    ) {
        dispatcher.registerInvoker(
            OrderAssignedToCourierEvent.class,
            new OrderAssignedToCourierInvoker()
        );
        dispatcher.registerInvoker(
            OrderCancelledEvent.class,
            new OrderCancelledInvoker()
        );
        dispatcher.registerInvoker(
            OrderDeliveredEvent.class,
            new OrderDeliveredInvoker()
        );
        dispatcher.registerInvoker(
            OrderDeliveryFailedEvent.class,
            new OrderDeliveryFailedInvoker()
        );
        dispatcher.registerInvoker(
            OrderOutForDeliveryEvent.class,
            new OrderOutForDeliveryInvoker()
        );
        dispatcher.registerInvoker(
            OrderPlacedEvent.class,
            new OrderPlacedInvoker()
        );
    }

    private void registerInventoryInvokers(
        final InvokerDispatcher dispatcher
    ) {
        dispatcher.registerInvoker(
            InventoryUpdatedEvent.class,
            new InventoryUpdatedInvoker()
        );
        dispatcher.registerInvoker(
            ProductCreatedEvent.class,
            new ProductCreatedInvoker()
        );
    }

}
