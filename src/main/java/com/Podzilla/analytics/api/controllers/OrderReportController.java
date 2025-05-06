package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.OrderAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * Controller for order-related reporting and analytics endpoints.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderReportController {

    /** Service for handling order analytics operations. */
    private final OrderAnalyticsService orderAnalyticsService;
}
