package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderAssignedToCourierEvent;

public class OrderAssignedToCourierInvoker
    implements Invoker<OrderAssignedToCourierEvent> {

    @Override
    public void invoke(final OrderAssignedToCourierEvent event) {
        // create a command and call its execute method
        System.out.println("Order Assigned To Courier Event Invoked: " + event);
    }
}
