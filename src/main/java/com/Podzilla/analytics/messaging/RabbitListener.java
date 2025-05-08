package com.Podzilla.analytics.messaging;

import org.springframework.beans.factory.annotation.Autowired;

import com.Podzilla.analytics.eventhandler.EventHandlerDispatcher;

public class RabbitListener {

    @Autowired
    private EventHandlerDispatcher dispatcher;
}
