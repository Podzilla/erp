package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Podzilla.analytics.services.CustomerAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerReportController {
    private final CustomerAnalyticsService customerAnalyticsService;
}
