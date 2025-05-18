package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderCancelledEvent;
import com.Podzilla.analytics.messaging.commands.order.CancelOrderCommand;
public class OrderCancelledInvoker
    implements Invoker<OrderCancelledEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderCancelledInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderCancelledEvent event) {
        CancelOrderCommand command = commandFactory
            .createCancelOrderCommand(
                event.getOrderId(),
                event.getReason(),
                event.getTimestamp()
            );
        command.execute();
    }
}
