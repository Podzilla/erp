package com.Podzilla.analytics.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.api.dtos.profit.ProfitByCategory;
import com.Podzilla.analytics.services.ProfitAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * REST controller for profit analytics operations.
 * Provides endpoints to analyze revenue, cost, and profit metrics.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/profit-analytics")
public class ProfitReportController {
    private final ProfitAnalyticsService profitAnalyticsService;


    @Operation(
            summary = "Get profit by product category",
            description = "Returns the revenue, cost, and profit metrics "
                    + "grouped by product category")
    @GetMapping("/by-category")
    public ResponseEntity<List<ProfitByCategory>> getProfitByCategory(
            @Valid @ModelAttribute final DateRangeRequest request) {

        List<ProfitByCategory> profitData =
                profitAnalyticsService.getProfitByCategory(
                        request.getStartDate(),
                        request.getEndDate());
        return ResponseEntity.ok(profitData);
    }
}
