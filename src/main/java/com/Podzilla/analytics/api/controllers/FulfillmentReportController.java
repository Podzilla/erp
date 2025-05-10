package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Podzilla.analytics.services.FulfillmentAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("fulfillment")
public class FulfillmentReportController {
    private final FulfillmentAnalyticsService fulfillmentAnalyticsService;
}
