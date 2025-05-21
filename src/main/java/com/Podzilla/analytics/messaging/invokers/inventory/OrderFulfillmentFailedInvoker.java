package com.Podzilla.analytics.messaging.invokers.inventory;
import org.springframework.beans.factory.annotation.Autowired;
import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.WarehouseOrderFulfillmentFailedEvent;
import com.Podzilla.analytics.messaging.commands.inventory.MarkOrderAsFailedToFulfillCommand;

public class OrderFulfillmentFailedInvoker
    implements Invoker<WarehouseOrderFulfillmentFailedEvent> {

    @Autowired
    private final CommandFactory commandFactory;

    public OrderFulfillmentFailedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final WarehouseOrderFulfillmentFailedEvent event) {
        MarkOrderAsFailedToFulfillCommand command =
            commandFactory.createMarkOrderAsFailedToFulfillCommand(
                event.getOrderId(),
                event.getReason(),
                event.getTimestamp()
            );
        command.execute();
    }

}
