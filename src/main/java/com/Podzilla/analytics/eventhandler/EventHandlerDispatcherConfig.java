package com.Podzilla.analytics.eventhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up event handler dispatcher.
 * This class is responsible for creating and configuring the event
 * handler dispatcher.
 */
@Configuration
public class EventHandlerDispatcherConfig {

    /**
     * Creates and returns an instance of EventHandlerDispatcher.
     *
     * @return the configured EventHandlerDispatcher
     */
    @Bean
    public EventHandlerDispatcher commandDispatcher() {
        EventHandlerDispatcher dispatcher = new EventHandlerDispatcher();

        // Register all event handlers here
        // Example:
        // dispatcher.registerHandler(aDTO.class, new aHandler());

        return dispatcher;
    }
}
