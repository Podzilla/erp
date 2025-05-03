package com.Podzilla.analytics.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.InventoryAnalyticsService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("inventory")
public class InventoryReportController {
    private final InventoryAnalyticsService inventoryAnalyticsService;

    @GetMapping("/value/by-category")
    public List<Map<String, Object>> getInventoryValueByCategory() {
        return inventoryAnalyticsService.getInventoryValueByCategory();
    }

    @GetMapping("/low-stock")
    public Page<Map<String, Object>> getLowStockProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return inventoryAnalyticsService.getLowStockProducts(page, size);
    }
}
