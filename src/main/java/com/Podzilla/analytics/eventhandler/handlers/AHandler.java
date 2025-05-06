package com.Podzilla.analytics.eventhandler.handlers;

import com.Podzilla.analytics.eventhandler.IEventHandler;
import com.Podzilla.analytics.eventhandler.DTOs.ADTO;

/**
 * Handler for processing ADTO events.
 * This handler performs actions based on the ADTO event.
 */
public class AHandler implements IEventHandler<ADTO> {

    /**
     * Handles the ADTO event.
     *
     * @param eventDto the event data transfer object
     */
    @Override
    public void handle(final ADTO eventDto) {
    }
}
