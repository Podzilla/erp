package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderDeliveredEvent;

public class OrderDeliveredInvoker
    implements Invoker<OrderDeliveredEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderDeliveredInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderDeliveredEvent event) {
        // create a command and call its execute method
        System.out.println("Order Delivered Event Invoked: " + event);
    }

}
