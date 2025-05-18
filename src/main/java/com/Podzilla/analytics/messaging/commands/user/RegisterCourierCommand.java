package com.Podzilla.analytics.messaging.commands.user;

import com.Podzilla.analytics.messaging.commands.Command;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import lombok.Builder;

@Builder
public class RegisterCourierCommand implements Command {

    private CourierAnalyticsService courierAnalyticsService;
    private String courierId;
    private String courierName;

    @Override
    public void execute() {
        courierAnalyticsService.saveCourier(
            courierId,
            courierName
        );
    }
}
