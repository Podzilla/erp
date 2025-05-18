package com.Podzilla.analytics.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.Podzilla.analytics.messaging.invokers.Invoker;

public class InvokerDispatcher {
    private final Map<Class<?>, Invoker<?>> invokers;

    public InvokerDispatcher() {
        this.invokers = new ConcurrentHashMap<>();
    }

    public <T> void registerInvoker(
        final Class<T> event, final Invoker<T> invoker
    ) {
        if (event == null || invoker == null) {
            throw new IllegalArgumentException(
                "Event and Invoker cannot be null"
            );
        }
        invokers.put(event, invoker);
    }

    @SuppressWarnings("unchecked")
    public <T> void dispatch(final T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        Invoker<T> invoker = (Invoker<T>) invokers.get(event.getClass());
        if (invoker != null) {
            invoker.invoke(event);
        } else {
            throw new RuntimeException("No invoker found for: "
                + event.getClass());
        }
    }
}
