package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.ProductAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling product analytics reports.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductReportController {
    /** Service for product analytics operations. */
    private final ProductAnalyticsService productAnalyticsService;
}
