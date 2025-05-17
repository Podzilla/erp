package com.Podzilla.analytics.messaging.invokers.user;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.CourierRegisteredEvent;

public class CourierRegisteredInvoker
    implements Invoker<CourierRegisteredEvent> {

    @Override
    public void invoke(final CourierRegisteredEvent event) {
        // create a command and call its execute method
        System.out.println("Courier Registered Event Invoked: " + event);
    }
}
