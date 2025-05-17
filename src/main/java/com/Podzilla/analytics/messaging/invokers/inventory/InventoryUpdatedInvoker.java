package com.Podzilla.analytics.messaging.invokers.inventory;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.InventoryUpdatedEvent;

public class InventoryUpdatedInvoker
    implements Invoker<InventoryUpdatedEvent> {

    @Override
    public void invoke(final InventoryUpdatedEvent event) {
        // create a command and call its execute method
        System.out.println("Inventory Updated Event Invoked: " + event);
    }

}
