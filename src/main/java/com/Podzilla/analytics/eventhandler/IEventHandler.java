package com.Podzilla.analytics.eventhandler;

/**
 * Generic interface for handling events.
 *
 * @param <T> the type of the event DTO
 */
public interface IEventHandler<T> {

    /**
     * Handles the given event DTO.
     *
     * @param eventDto the data transfer object representing the event
     */
    void handle(T eventDto);
}
