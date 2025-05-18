package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.commands.order.MarkOrderAsDeliveredCommand;
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
        MarkOrderAsDeliveredCommand command =
            commandFactory.createMarkOrderAsDeliveredCommand(
                event.getOrderId(),
                event.getCourierRating(),
                event.getTimestamp()
            );
        command.execute();
    }

}
