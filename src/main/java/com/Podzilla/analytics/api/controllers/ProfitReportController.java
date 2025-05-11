package com.Podzilla.analytics.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.api.dtos.profit.ProfitByCategory;
import com.Podzilla.analytics.services.ProfitAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * REST controller for profit analytics operations.
 * Provides endpoints to analyze revenue, cost, and profit metrics.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/profit")
public class ProfitReportController {
    private final ProfitAnalyticsService profitAnalyticsService;


    @Operation(
            summary = "Get profit by product category",
            description = "Returns the revenue, cost, and profit metrics "
                    + "grouped by product category")
    @GetMapping("/by-category")
    public ResponseEntity<List<ProfitByCategory>> getProfitByCategory(
            @Valid @ModelAttribute final DateRangeRequest request) {

        // Validate request parameters
        if (request.getStartDate() == null || request.getEndDate() == null) {
            log.warn("Missing date parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.emptyList());
        }

        // Validate date range
        if (request.getStartDate().isAfter(request.getEndDate())) {
            log.warn("Invalid date range: start date after end date");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.emptyList());
        }

        try {
            List<ProfitByCategory> profitData =
                    profitAnalyticsService.getProfitByCategory(
                            request.getStartDate(),
                            request.getEndDate());
            return ResponseEntity.ok(profitData);
        } catch (Exception ex) {
            log.error("Error getting profit data", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
