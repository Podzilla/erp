package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderDeliveryFailedEvent;

public class OrderDeliveryFailedInvoker
    implements Invoker<OrderDeliveryFailedEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderDeliveryFailedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderDeliveryFailedEvent event) {
        // create a command and call its execute method
        System.out.println("Order Delivery Failed Event Invoked: " + event);
    }

}
