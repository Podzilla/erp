package com.Podzilla.analytics.messaging.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Podzilla.analytics.services.CustomerAnalyticsService;
import com.Podzilla.analytics.messaging.commands.user.RegisterCustomerCommand;

@Component
public class CommandFactory {

    @Autowired
    private CustomerAnalyticsService customerAnalyticsService;

    public RegisterCustomerCommand createRegisterCustomerCommand(
        final String customerId,
        final String customerName
    ) {
        return RegisterCustomerCommand.builder()
            .customerAnalyticsService(customerAnalyticsService)
            .customerId(customerId)
            .customerName(customerName)
            .build();
    }

}
