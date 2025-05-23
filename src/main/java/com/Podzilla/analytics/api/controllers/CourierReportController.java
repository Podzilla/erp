package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.api.dtos.courier.CourierAverageRatingResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierDeliveryCountResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierPerformanceReportResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierSuccessRateResponse;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Courier Reports", description = "Endpoints for courier"
        + " analytics and performance metrics")
@RestController
@RequestMapping("/courier-analytics")
@RequiredArgsConstructor
@Slf4j
public final class CourierReportController {

    private final CourierAnalyticsService courierAnalyticsService;

    @Operation(summary = "Get delivery counts", description = "Returns the"
            + " total number of deliveries completed by each courier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved"
            + " delivery counts")
    @GetMapping("/delivery-counts")
    public ResponseEntity<List<CourierDeliveryCountResponse>> getDeliveryCounts(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /courier-analytics/delivery-counts "
                + "with attributes: {}", dateRange);
        List<CourierDeliveryCountResponse> counts = courierAnalyticsService
                .getCourierDeliveryCounts(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(counts);
    }

    @Operation(summary = "Get courier success rate", description = "Returns "
            + " the success rate of each courier")
    @GetMapping("/success-rate")
    public ResponseEntity<List<CourierSuccessRateResponse>> getSuccessRate(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /courier-analytics/success-rate "
                + "with attributes: {}",
                dateRange);
        List<CourierSuccessRateResponse> rates = courierAnalyticsService
                .getCourierSuccessRate(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(rates);
    }

    @Operation(summary = "Get average courier ratings", description = "Fetches "
            + " the average rating received by each courier")
    @GetMapping("/average-rating")
    public ResponseEntity<List<CourierAverageRatingResponse>> getAverageRating(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /courier-analytics/average-rating "
                + "with attributes: {}", dateRange);
        List<CourierAverageRatingResponse> ratings = courierAnalyticsService
                .getCourierAverageRating(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get courier performance report", description = ""
            + "Returns a detailed performance report of each courier")
    @GetMapping("/performance-report")
    public ResponseEntity<List<CourierPerformanceReportResponse>> getReport(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /courier-analytics/performance-report "
                + "with attributes: {}", dateRange);
        List<CourierPerformanceReportResponse> report = courierAnalyticsService
                .getCourierPerformanceReport(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(report);
    }
}
