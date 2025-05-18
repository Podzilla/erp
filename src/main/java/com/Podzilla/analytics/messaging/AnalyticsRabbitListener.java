package com.Podzilla.analytics.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.podzilla.mq.EventsConstants;
import org.springframework.beans.factory.annotation.Autowired;

import com.podzilla.mq.events.BaseEvent;

import org.springframework.stereotype.Service;



@Service
public class AnalyticsRabbitListener {

    @Autowired
    private InvokerDispatcher dispatcher;

    @RabbitListener(
        queues = EventsConstants.ANALYTICS_USER_EVENT_QUEUE
    )
    public void handleUserEvents(final BaseEvent userEvent) {
        dispatcher.dispatch(userEvent);
    }

    @RabbitListener(
        queues = EventsConstants.ANALYTICS_ORDER_EVENT_QUEUE
    )
    public void handleOrderEvents(final BaseEvent orderEvent) {
        dispatcher.dispatch(orderEvent);
    }

    @RabbitListener(
        queues = EventsConstants.ANALYTICS_INVENTORY_EVENT_QUEUE
    )
    public void handleInventoryEvents(final BaseEvent inventoryEvent) {
        dispatcher.dispatch(inventoryEvent);
    }
}
