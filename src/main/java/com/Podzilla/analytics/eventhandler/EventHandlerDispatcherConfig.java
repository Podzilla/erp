package com.Podzilla.analytics.eventhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventHandlerDispatcherConfig {

    @Bean
    public EventHandlerDispatcher commandDispatcher() {
        EventHandlerDispatcher dispatcher = new EventHandlerDispatcher();

<<<<<<< HEAD:src/main/java/com/Podzilla/analytics/eventhandler/eventHandlerDispatcherConfig.java
        //TODO should add all the events here 
        //Example: 
        // dispatcher.registerHandler(aDTO.class, new aHandler()); 
=======
        // Register all event handlers here
        // Example:
        // dispatcher.registerHandler(aDTO.class, new aHandler());

>>>>>>> dev:src/main/java/com/Podzilla/analytics/eventhandler/EventHandlerDispatcherConfig.java
        return dispatcher;
    }
}
