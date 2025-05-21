package com.Podzilla.analytics.eventhandler;


public interface IEventHandler<T> { //T should be the DTO of the event
    void handle(T eventDto);
}
