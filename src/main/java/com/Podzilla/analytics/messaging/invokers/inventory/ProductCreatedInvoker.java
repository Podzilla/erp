package com.Podzilla.analytics.messaging.invokers.inventory;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.ProductCreatedEvent;

public class ProductCreatedInvoker
    implements Invoker<ProductCreatedEvent> {

    @Override
    public void invoke(final ProductCreatedEvent event) {
        // create a command and call its execute method
        System.out.println("Product Created Event Invoked: " + event);
    }
}
