package com.Podzilla.analytics.eventhandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dispatches events to the appropriate handlers based on the DTO type.
 * This class is responsible for registering event handlers and dispatching
 * events to the corresponding handler based on the DTO class.
 */
public final class EventHandlerDispatcher {
    /** A map to store handlers for different DTO types. */
    private final Map<Class<?>, IEventHandler<?>> handlers;

    /**
     * Constructor initializes the handlers map.
     */
    public EventHandlerDispatcher() {
        handlers = new ConcurrentHashMap<>();
    }

    /**
     * Registers a handler for a specific DTO class.
     *
     * @param <T>     the type of the DTO
     * @param dto     the class type of the DTO
     * @param handler the handler for the DTO
     * @throws IllegalArgumentException if the dto or handler is null
     */
    public <T> void registerHandler(final Class<T> dto,
            final IEventHandler<T> handler) {
        if (dto == null || handler == null) {
            throw new IllegalArgumentException("DTO and Handler"
                    + " cannot be null");
        }
        handlers.put(dto, handler);
    }

    /**
     * Dispatches an event (DTO) to the appropriate handler based on
     * the DTO class type.
     *
     * @param <T> the type of the DTO
     * @param dto the DTO to be dispatched
     * @throws RuntimeException         if no handler is found for
     *                                  the DTO class
     * @throws IllegalArgumentException if the DTO is null
     */
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
