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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Podzilla.analytics.services.RevenueReportService;
import com.Podzilla.analytics.utils.ValidationUtils;

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
        @RequestParam Period period
    ) {
        // --- Validation ---
        ResponseEntity<List<RevenueSummaryResponse>> validationError = ValidationUtils.validateDateRange(startDate, endDate);
        if (validationError != null) {
            return validationError;
        }
        validationError = ValidationUtils.validateEnumNotNull(period);
        if (validationError != null) {
            return validationError;
        }

        RevenueSummaryRequest requestDTO = RevenueSummaryRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .period(period)
                .build();

        return ResponseEntity.ok(revenueReportService.getRevenueSummary(requestDTO));
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<RevenueByCategoryResponse>> getRevenueByCategory(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        // --- Validation ---
        ResponseEntity<List<RevenueByCategoryResponse>> validationError = ValidationUtils.validateDateRange(startDate, endDate);
        if (validationError != null) {
            return validationError;
        }
        // --- End Validation ---

        List<RevenueByCategoryResponse> summaryList = revenueReportService.getRevenueByCategory(startDate, endDate);
        return ResponseEntity.ok(summaryList);
    }

}
