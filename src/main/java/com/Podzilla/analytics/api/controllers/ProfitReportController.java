package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.ProfitAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling profit analytics reports.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/profit")
public class ProfitReportController {
    /** Service for profit analytics operations. */
    private final ProfitAnalyticsService profitAnalyticsService;
}
