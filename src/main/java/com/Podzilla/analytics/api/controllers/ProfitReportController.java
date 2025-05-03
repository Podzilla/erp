package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.ProfitAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profit")
public class ProfitReportController {
    private final ProfitAnalyticsService profitAnalyticsService;
}
