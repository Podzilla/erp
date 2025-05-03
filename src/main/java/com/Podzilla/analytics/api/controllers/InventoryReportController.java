package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.InventoryAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryReportController {
    private final InventoryAnalyticsService inventoryAnalyticsService;
}
