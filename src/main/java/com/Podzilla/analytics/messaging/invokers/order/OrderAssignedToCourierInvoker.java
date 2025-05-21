package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderAssignedToCourierEvent;
import com.Podzilla.analytics.messaging.commands.order.AssignCourierToOrderCommand;

public class OrderAssignedToCourierInvoker
    implements Invoker<OrderAssignedToCourierEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderAssignedToCourierInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderAssignedToCourierEvent event) {
        AssignCourierToOrderCommand command = commandFactory
            .createAssignCourierToOrderCommand(
                event.getOrderId(),
                event.getCourierId()
            );
        command.execute();
    }
}
