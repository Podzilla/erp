package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderCancelledEvent;

public class OrderCancelledInvoker
    implements Invoker<OrderCancelledEvent> {

    @Override
    public void invoke(final OrderCancelledEvent event) {
        // create a command and call its execute method
        System.out.println("Order Cancelled Event Invoked: " + event);
    }
}
