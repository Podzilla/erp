package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.dtos.CourierAverageRatingResponse;
import com.Podzilla.analytics.api.dtos.CourierDeliveryCountResponse;
import com.Podzilla.analytics.api.dtos.CourierPerformanceReportResponse;
import com.Podzilla.analytics.api.dtos.CourierSuccessRateResponse;
import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/couriers")
public class CourierReportController {
    private final CourierAnalyticsService courierAnalyticsService;

    @GetMapping("/delivery-counts")
    public ResponseEntity<List<CourierDeliveryCountResponse>> getCourierDeliveryCounts(
            @Valid @ModelAttribute DateRangeRequest dateRange) {

        List<CourierDeliveryCountResponse> counts = courierAnalyticsService
                .getCourierDeliveryCounts(dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/success-rate")
    public ResponseEntity<List<CourierSuccessRateResponse>> getCourierSuccessRate(
            @Valid @ModelAttribute DateRangeRequest dateRange) {

        List<CourierSuccessRateResponse> rates = courierAnalyticsService.getCourierSuccessRate(dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<List<CourierAverageRatingResponse>> getCourierAverageRating(
            @Valid @ModelAttribute DateRangeRequest dateRange) {

        List<CourierAverageRatingResponse> ratings = courierAnalyticsService.getCourierAverageRating(
                dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/performance-report")
    public ResponseEntity<List<CourierPerformanceReportResponse>> getCourierPerformanceReport(
            @Valid @ModelAttribute DateRangeRequest dateRange) {

        List<CourierPerformanceReportResponse> report = courierAnalyticsService.getCourierPerformanceReport(
                dateRange.getStartDate(),
                dateRange.getEndDate());
        return ResponseEntity.ok(report);
    }
}