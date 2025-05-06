package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.FulfillmentAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling fulfillment analytics reports.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/fulfillment")
public class FulfillmentReportController {
    /** Service for fulfillment analytics operations. */
    private final FulfillmentAnalyticsService fulfillmentAnalyticsService;
}
