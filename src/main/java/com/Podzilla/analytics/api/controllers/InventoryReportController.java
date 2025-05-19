package com.Podzilla.analytics.api.controllers;

import org.springframework.data.domain.Page;

import com.Podzilla.analytics.api.dtos.inventory.InventoryValueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.inventory.LowStockProductResponse;
import com.Podzilla.analytics.api.dtos.PaginationRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Podzilla.analytics.services.InventoryAnalyticsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Inventory Reports", description = "APIs for inventory analytics"
        + "and reporting")
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory-analytics")
@Slf4j
public class InventoryReportController {
    private final InventoryAnalyticsService inventoryAnalyticsService;

    @Operation(summary = "Get inventory"
            + "value by category", description = "Returns"
                    + "the total value of inventory "
                    + "grouped by product categories")
    @GetMapping("/value/by-category")
    public List<InventoryValueByCategoryResponse> getInventoryValueByCategory(

    ) {
        log.info("Request on: /inventory-analytics/value/by-category");
        return inventoryAnalyticsService.getInventoryValueByCategory();
    }

    @Operation(summary = "Get low stock products", description = "Returns "
            + "a paginated list of products that are running low on stock")
    @GetMapping("/low-stock")
    public Page<LowStockProductResponse> getLowStockProducts(
            @Valid @ModelAttribute final PaginationRequest paginationRequest) {
        log.info("Request on: /inventory-analytics/low-stock"
                + " with attributes: {}", paginationRequest);
        return inventoryAnalyticsService.getLowStockProducts(
                paginationRequest.getPage(),
                paginationRequest.getSize());
    }
}
