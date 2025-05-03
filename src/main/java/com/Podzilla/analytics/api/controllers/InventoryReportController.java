package com.Podzilla.analytics.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.dtos.InventoryValueByCategoryDTO;
import com.Podzilla.analytics.api.dtos.LowStockProductDTO;
import com.Podzilla.analytics.services.InventoryAnalyticsService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryReportController {
    private final InventoryAnalyticsService inventoryAnalyticsService;

    @GetMapping("/value/by-category")
    public List<InventoryValueByCategoryDTO> getInventoryValueByCategory() {
        return inventoryAnalyticsService.getInventoryValueByCategory();
    }

    @GetMapping("/low-stock")
    public Page<LowStockProductDTO> getLowStockProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return inventoryAnalyticsService.getLowStockProducts(page, size);
    }
}
