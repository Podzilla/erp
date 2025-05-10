package com.Podzilla.analytics.eventhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerDispatcherConfig {

    @Bean
    public EventHandlerDispatcher commandDispatcher() {
        EventHandlerDispatcher dispatcher = new EventHandlerDispatcher();

        // Register all event handlers here
        // Example:
        // dispatcher.registerHandler(aDTO.class, new aHandler());

        return dispatcher;
    }
}
