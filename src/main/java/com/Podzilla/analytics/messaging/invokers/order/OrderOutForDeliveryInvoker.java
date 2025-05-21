package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderOutForDeliveryEvent;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsOutForDeliveryCommand;

public class OrderOutForDeliveryInvoker
    implements Invoker<OrderOutForDeliveryEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderOutForDeliveryInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderOutForDeliveryEvent event) {
        MarkOrderAsOutForDeliveryCommand command = commandFactory
            .createMarkOrderAsOutForDeliveryCommand(
                event.getOrderId(),
                event.getTimestamp()
            );
        command.execute();
    }
}
