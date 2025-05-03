package com.Podzilla.analytics.api.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.DTOs.RevenueByCategoryResponse;
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
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam Period period // Spring will try to convert the String param to your Period enum
    ) {
        // --- Validation ---
        if (startDate == null || endDate == null || period == null) {
             return ResponseEntity.badRequest().body(null);
        }

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }

        RevenueSummaryRequest requestDTO = RevenueSummaryRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .period(period)
                .build();

        return ResponseEntity.ok(revenueReportService.getRevenueSummary(requestDTO));
    }
 @GetMapping("/by-category") // Specific path for this report
    public ResponseEntity<List<RevenueByCategoryResponse>> getRevenueByCategory(
        // Receive parameters from the URL using @RequestParam
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        // --- Validation ---
        if (startDate == null || endDate == null) {
             return ResponseEntity.badRequest().body(null); // Consider a specific error response body
        }

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null); // Consider a specific error response body
        }
        // --- End Validation ---

        // --- Call the Service Layer ---
        List<RevenueByCategoryResponse> summaryList = revenueReportService.getRevenueByCategory(startDate, endDate);

        // Return the list of summary data with OK status
        return ResponseEntity.ok(summaryList);
    }

}