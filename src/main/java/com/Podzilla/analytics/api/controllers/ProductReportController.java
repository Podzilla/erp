package com.Podzilla.analytics.api.controllers;
import java.time.LocalDate;
import java.util.List; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Podzilla.analytics.api.DTOs.TopSellerRequest;
import com.Podzilla.analytics.api.DTOs.TopSellerRequest.SortBy; 
import com.Podzilla.analytics.api.DTOs.TopSellerResponse;
import com.Podzilla.analytics.services.ProductAnalyticsService;

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
        @RequestParam(defaultValue = "REVENUE") SortBy sortBy // Spring can convert String param to enum
    ) {
        // --- Validation ---
        if (startDate == null || endDate == null) {
             return ResponseEntity.badRequest().body(null);
        }

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null); 
        }

        if (limit == null || limit <= 0) {
             return ResponseEntity.badRequest().body(null);
        }

        if (sortBy == null) {
             // This case should be handled by @RequestParam's conversion or defaultValue,
             // but an explicit check can add robustness if the string value is invalid.
             return ResponseEntity.badRequest().body(null); // Invalid sortBy value
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