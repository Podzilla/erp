package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderDeliveryFailedEvent;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsFailedToDeliverCommand;

public class OrderDeliveryFailedInvoker
    implements Invoker<OrderDeliveryFailedEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderDeliveryFailedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderDeliveryFailedEvent event) {
        MarkOrderAsFailedToDeliverCommand command =
            commandFactory.createMarkOrderAsFailedToDeliverCommand(
                event.getOrderId(),
                event.getReason(),
                event.getTimestamp()
            );
        command.execute();
    }

}
