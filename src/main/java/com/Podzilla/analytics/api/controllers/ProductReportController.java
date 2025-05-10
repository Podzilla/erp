package com.Podzilla.analytics.api.controllers;
import java.time.LocalDate;
import java.util.List; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Podzilla.analytics.api.dtos.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.TopSellerRequest.SortBy; 
import com.Podzilla.analytics.api.dtos.TopSellerResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Podzilla.analytics.services.ProductAnalyticsService;
import com.Podzilla.analytics.utils.ValidationUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products") 
public class ProductReportController {

    private final ProductAnalyticsService productAnalyticsService;

    @GetMapping("/top-sellers") 
    public ResponseEntity<List<TopSellerResponse>> getTopSellers(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "REVENUE") SortBy sortBy
    ) {
        // --- Validation ---
        ResponseEntity<List<TopSellerResponse>> validationError = ValidationUtils.validateDateRange(startDate, endDate);
        if (validationError != null) {
            return validationError;
        }

        validationError = ValidationUtils.validatePositiveLimit(limit);
        if (validationError != null) {
            return validationError;
        }

        validationError = ValidationUtils.validateEnumNotNull(sortBy);
        if (validationError != null) {
            return validationError;
        }
        // --- End Validation ---

        TopSellerRequest requestDTO = TopSellerRequest.builder()
                                        .startDate(startDate)
                                        .endDate(endDate)
                                        .limit(limit)
                                        .sortBy(sortBy)
                                        .build();

        List<TopSellerResponse> topSellersList = productAnalyticsService.getTopSellers(requestDTO);

        return ResponseEntity.ok(topSellersList);
    }
}
