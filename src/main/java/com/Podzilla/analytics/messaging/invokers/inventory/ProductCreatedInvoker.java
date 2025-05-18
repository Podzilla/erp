package com.Podzilla.analytics.messaging.invokers.inventory;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.commands.inventory.CreateProductCommand;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.ProductCreatedEvent;

public class ProductCreatedInvoker
    implements Invoker<ProductCreatedEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public ProductCreatedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final ProductCreatedEvent event) {
        CreateProductCommand command = commandFactory
            .createCreateProductCommand(
                event.getProductId(),
                event.getName(),
                event.getCategory(),
                event.getCost(),
                event.getLowStockThreshold()
        );
        command.execute();
    }
}
