package com.Podzilla.analytics.messaging.invokers;

public interface Invoker<T> { // T should be the BaseEvent subclass of the event
    void invoke(T event);
}
