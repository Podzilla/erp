package com.Podzilla.analytics.eventhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Podzilla.analytics.eventhandler.DTOs.aDTO;
import com.Podzilla.analytics.eventhandler.handlers.aHandler;

@Configuration
public class eventHandlerDispatcherConfig {

    @Bean
    public EventHandlerDispatcher commandDispatcher() {
        EventHandlerDispatcher dispatcher = new EventHandlerDispatcher();

        //TODO should add all the events here 
        //Example: 
        // dispatcher.registerHandler(aDTO.class, new aHandler()); 
        return dispatcher;
    }
}
