package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderOutForDeliveryEvent;

public class OrderOutForDeliveryInvoker
    implements Invoker<OrderOutForDeliveryEvent> {

    @Override
    public void invoke(final OrderOutForDeliveryEvent event) {
        // create a command and call its execute method
        System.out.println("Order Out For Delivery Event Invoked: " + event);
    }
}
