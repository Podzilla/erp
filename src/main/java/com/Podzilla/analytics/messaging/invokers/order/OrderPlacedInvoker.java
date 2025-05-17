package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderPlacedEvent;

public class OrderPlacedInvoker
    implements Invoker<OrderPlacedEvent> {

    @Override
    public void invoke(final OrderPlacedEvent event) {
        // create a command and call its execute method
        System.out.println("Order Placed Event Invoked: " + event);
    }

}
