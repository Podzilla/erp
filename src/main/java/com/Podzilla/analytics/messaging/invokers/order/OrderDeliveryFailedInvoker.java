package com.Podzilla.analytics.messaging.invokers.order;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderDeliveryFailedEvent;

public class OrderDeliveryFailedInvoker
    implements Invoker<OrderDeliveryFailedEvent> {

    @Override
    public void invoke(final OrderDeliveryFailedEvent event) {
        // create a command and call its execute method
        System.out.println("Order Delivery Failed Event Invoked: " + event);
    }

}
