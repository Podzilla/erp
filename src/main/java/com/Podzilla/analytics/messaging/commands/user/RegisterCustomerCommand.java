package com.Podzilla.analytics.messaging.commands.user;

import com.Podzilla.analytics.messaging.commands.Command;
import com.Podzilla.analytics.services.CustomerAnalyticsService;

import lombok.Builder;

@Builder
public class RegisterCustomerCommand implements Command {

    private CustomerAnalyticsService customerAnalyticsService;
    private String customerId;
    private String customerName;

    @Override
    public void execute() {
        customerAnalyticsService.saveCustomer(
            customerId,
            customerName
        );
    }

}
