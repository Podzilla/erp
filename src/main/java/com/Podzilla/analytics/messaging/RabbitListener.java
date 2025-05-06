package com.Podzilla.analytics.messaging;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.eventhandler.EventHandlerDispatcher;

public class RabbitListener {

    /**
     * The dispatcher responsible for handling and dispatching events received
     * from the RabbitMQ message broker.
     */
    @Autowired
    private EventHandlerDispatcher dispatcher;
}
