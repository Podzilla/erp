package com.Podzilla.analytics.messaging.invokers.order;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.commands.order.PlaceOrderCommand;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.OrderPlacedEvent;

public class OrderPlacedInvoker
    implements Invoker<OrderPlacedEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public OrderPlacedInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final OrderPlacedEvent event) {
        System.out.println("Invoking PlaceOrderInvoker henaaa");
        PlaceOrderCommand command = commandFactory
            .createPlaceOrderCommand(
                event.getOrderId(),
                event.getCustomerId(),
                event.getItems(),
                event.getDeliveryAddress(),
                event.getTotalAmount(),
                event.getTimestamp()
            );
        command.execute();
    }

}
