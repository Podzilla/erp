package com.Podzilla.analytics.messaging.invokers.user;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.CourierRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import com.Podzilla.analytics.messaging.commands.CommandFactory;
import com.Podzilla.analytics.messaging.commands.user.RegisterCourierCommand;


public class CourierRegisteredInvoker
    implements Invoker<CourierRegisteredEvent> {

    @Autowired
    private final CommandFactory commandFactory;
    public CourierRegisteredInvoker(final CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void invoke(final CourierRegisteredEvent event) {
        RegisterCourierCommand command = commandFactory
            .createRegisterCourierCommand(
                event.getCourierId(),
                event.getName()
        );
        command.execute();
    }
}
