package com.Podzilla.analytics.messaging.invokers.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.CustomerRegisteredEvent;
import com.Podzilla.analytics.messaging.commands.user.RegisterCustomerCommand;

public class CustomerRegisteredInvoker
    implements Invoker<CustomerRegisteredEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public CustomerRegisteredInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final CustomerRegisteredEvent event) {
        RegisterCustomerCommand command = commandFactory
            .createRegisterCustomerCommand(
                event.getCustomerId(),
                event.getName()
            );
        command.execute();
    }

}
