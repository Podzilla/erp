package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderDeliveredEvent;

public class OrderDeliveredInvoker
    implements Invoker<OrderDeliveredEvent> {

    @Override
    public void invoke(final OrderDeliveredEvent event) {
        // create a command and call its execute method
        System.out.println("Order Delivered Event Invoked: " + event);
    }

}
