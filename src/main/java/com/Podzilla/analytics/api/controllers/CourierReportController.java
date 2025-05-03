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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Courier Reports", description = "Endpoints for courier analytics and performance metrics")
@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierReportController {

    private final CourierAnalyticsService courierAnalyticsService;

    @Operation(summary = "Get delivery counts", description = "Returns the total number of deliveries (both successful and failed) completed by each courier within the specified date range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved delivery counts")
    @GetMapping("/delivery-counts")
    public ResponseEntity<List<CourierDeliveryCountResponse>> getCourierDeliveryCounts(
            @Valid @ModelAttribute DateRangeRequest dateRange) {

        List<CourierDeliveryCountResponse> counts = courierAnalyticsService
                .getCourierDeliveryCounts(dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(counts);
    }

    @Operation(summary = "Get courier success rate", description = "Returns the success rate of each courier within the given date range")
    @GetMapping("/success-rate")
    public ResponseEntity<List<CourierSuccessRateResponse>> getCourierSuccessRate(
            @Valid @ModelAttribute DateRangeRequest dateRange) {
        List<CourierSuccessRateResponse> rates = courierAnalyticsService
                .getCourierSuccessRate(dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(rates);
    }

    @Operation(summary = "Get average courier ratings", description = "Fetches the average rating received by each courier in the specified date range")
    @GetMapping("/average-rating")
    public ResponseEntity<List<CourierAverageRatingResponse>> getCourierAverageRating(
            @Valid @ModelAttribute DateRangeRequest dateRange) {
        List<CourierAverageRatingResponse> ratings = courierAnalyticsService
                .getCourierAverageRating(dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get courier performance report", description = "Returns a detailed performance report of each courier including deliveries, ratings, and success rate")
    @GetMapping("/performance-report")
    public ResponseEntity<List<CourierPerformanceReportResponse>> getCourierPerformanceReport(
            @Valid @ModelAttribute DateRangeRequest dateRange) {
        List<CourierPerformanceReportResponse> report = courierAnalyticsService
                .getCourierPerformanceReport(dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(report);
    }
}
