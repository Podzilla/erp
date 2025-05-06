package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.CustomerAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling customer analytics reports.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerReportController {
    /** Service for customer analytics operations. */
    private final CustomerAnalyticsService customerAnalyticsService;
}
