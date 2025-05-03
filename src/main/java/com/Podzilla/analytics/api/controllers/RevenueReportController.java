package com.Podzilla.analytics.api.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.DTOs.RevenueSummaryRequest;
import com.Podzilla.analytics.api.DTOs.RevenueSummaryRequest.Period;
import com.Podzilla.analytics.api.DTOs.RevenueSummaryResponse;
import com.Podzilla.analytics.services.RevenueReportService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/revenue")
public class RevenueReportController {
    private final RevenueReportService revenueReportService;

      @GetMapping("/summary")
    public ResponseEntity<List<RevenueSummaryResponse>> getRevenueSummary(
        // Receive parameters from the URL using @RequestParam
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam Period period // Spring will try to convert the String param to your Period enum
    ) {
        // --- Validation ---
        if (startDate == null || endDate == null || period == null) {
             // More specific error message is better in production
             return ResponseEntity.badRequest().body(null);
        }

        if (startDate.isAfter(endDate)) {
            // More specific error message is better in production
            return ResponseEntity.badRequest().body(null);
        }
        // --- End Validation ---

        // --- Construct the Request DTO to pass to the service ---
        RevenueSummaryRequest requestDTO = RevenueSummaryRequest.builder()
                // Convert LocalDate back to Date if your DTO/Service still uses Date
                .startDate(startDate)
                .endDate(endDate)
                .period(period)
                .build();

        // --- Call the Service Layer with the DTO ---
        // Ensure your ProfitAnalyticsService has a method like:
        // public RevenueSummaryResponse getRevenueSummary(RevenueSummaryRequest request);
        return ResponseEntity.ok(revenueReportService.getRevenueSummary(requestDTO));
    }}