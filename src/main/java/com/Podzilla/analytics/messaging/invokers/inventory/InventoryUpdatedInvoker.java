package com.Podzilla.analytics.messaging.invokers.inventory;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.commands.inventory.UpdateInventoryCommand;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.InventoryUpdatedEvent;

public class InventoryUpdatedInvoker
        implements Invoker<InventoryUpdatedEvent> {

    @Autowired
    private final CommandFactory commandFactory;

    public InventoryUpdatedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final InventoryUpdatedEvent event) {
        event.getProductSnapshots().stream().map(
                snapshot -> commandFactory.createUpdateInventoryCommand(
                        snapshot.getProductId(),
                        snapshot.getNewQuantity(),
                        event.getTimestamp()))
                .forEach(UpdateInventoryCommand::execute);
    }

}
