package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderCancelledEvent;

public class OrderCancelledInvoker
    implements Invoker<OrderCancelledEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderCancelledInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderCancelledEvent event) {
        // create a command and call its execute method
        System.out.println("Order Cancelled Event Invoked: " + event);
    }
}
