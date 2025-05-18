package com.Podzilla.analytics.messaging.invokers;

import java.lang.reflect.Constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Podzilla.analytics.messaging.commands.CommandFactory;

@Component
public class InvokerFactory {

    @Autowired
    private final CommandFactory commandFactory;

    public InvokerFactory(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public <E, T extends Invoker<E>> T createInvoker(
        final Class<T> invokerClass
    ) {
    try {
        Constructor<T> constructor =
            invokerClass.getConstructor(CommandFactory.class);
        return constructor.newInstance(commandFactory);
    } catch (Exception e) {
        throw new RuntimeException(
            "Failed to create invoker of type: " + invokerClass.getName(), e
        );
    }
}

}

