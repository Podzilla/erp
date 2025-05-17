package com.Podzilla.analytics.messaging.invokers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.invokers.user.CustomerRegisteredInvoker;

@Component
public class InvokerFactory {

    @Autowired
    private final CommandFactory commandFactory;

    public InvokerFactory(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public CustomerRegisteredInvoker createRegisterCustomerInvoker() {
        return new CustomerRegisteredInvoker(commandFactory);
    }
}
