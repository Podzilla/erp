package com.Podzilla.analytics.eventhandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EventHandlerDispatcher {
    private final Map<Class<?>, IEventHandler<?>> handlers;

    public EventHandlerDispatcher() {
        handlers = new ConcurrentHashMap<>();
    }

    public <T> void registerHandler(final Class<T> dto,
            final IEventHandler<T> handler) {
        if (dto == null || handler == null) {
            throw new IllegalArgumentException("DTO and Handler"
                    + " cannot be null");
        }
        handlers.put(dto, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> void dispatch(final T dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Event DTO cannot be null");
        }

        IEventHandler<T> handler = (IEventHandler<T>) handlers
                .get(dto.getClass());
        if (handler != null) {
            handler.handle(dto);
        } else {
            throw new RuntimeException("No handler found for: "
                    + dto.getClass());
        }
    }
}
