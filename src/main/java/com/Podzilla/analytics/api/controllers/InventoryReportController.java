package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.InventoryAnalyticsService;

import lombok.RequiredArgsConstructor;

/**
 * Controller for inventory-related analytics endpoints.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryReportController {

    /** Service for handling inventory analytics logic. */
    private final InventoryAnalyticsService inventoryAnalyticsService;
}
