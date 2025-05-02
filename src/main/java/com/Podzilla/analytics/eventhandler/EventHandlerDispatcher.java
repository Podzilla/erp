package com.Podzilla.analytics.eventhandler;

import java.util.HashMap;

public class EventHandlerDispatcher {
    // dto , event
    HashMap<Class<?>, IEventHandler<?>> handlers;

    public <T> void registerHandler(Class<T> dto, IEventHandler<T> handler) {
        handlers.put(dto, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> void dispatch(T dto) {
        IEventHandler<T> handler = (IEventHandler<T>) handlers.get(dto.getClass());
        if (handler != null) {
            handler.handle(dto);
        } else {
            throw new RuntimeException("No handler found for: " + dto.getClass());
        }
    }
}
