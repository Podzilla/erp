package com.Podzilla.analytics.messaging.invokers.user;

import com.Podzilla.analytics.messaging.invokers.Invoker;
import com.podzilla.mq.events.CustomerRegisteredEvent;

public class CustomerRegisteredInvoker
    implements Invoker<CustomerRegisteredEvent> {

    @Override
    public void invoke(final CustomerRegisteredEvent event) {
        // create a command and call its execute method
        System.out.println("Customer Registered Event Invoked: " + event);
    }


}
