package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.CourierAverageRatingResponse;
import com.Podzilla.analytics.api.dtos.CourierDeliveryCountResponse;
import com.Podzilla.analytics.api.dtos.CourierPerformanceReportResponse;
import com.Podzilla.analytics.api.dtos.CourierSuccessRateResponse;
import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling courier analytics reports.
 */
@Tag(name = "Courier Reports", description = "Endpoints for courier"
        + " analytics and performance metrics")
@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public final class CourierReportController {

    /** Service for courier analytics operations. */
    private final CourierAnalyticsService courierAnalyticsService;

    /**
     * Retrieves the total number of deliveries completed by each courier within
     * the specified date range.
     *
     * @param dateRange the date range for the report
     * @return list of delivery count responses
     */
    @Operation(summary = "Get delivery counts", description = "Returns the"
            + " total number of deliveries completed by each courier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved"
            + " delivery counts")
    @GetMapping("/delivery-counts")
    public ResponseEntity<List<CourierDeliveryCountResponse>> getDeliveryCounts(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        List<CourierDeliveryCountResponse> counts = courierAnalyticsService
                .getCourierDeliveryCounts(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(counts);
    }

    /**
     * Retrieves the success rate of each courier within the specified date
     * range.
     *
     * @param dateRange the date range for the report
     * @return list of success rate responses
     */
    @Operation(summary = "Get courier success rate", description = "Returns "
            + " the success rate of each courier")
    @GetMapping("/success-rate")
    public ResponseEntity<List<CourierSuccessRateResponse>> getSuccessRate(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        List<CourierSuccessRateResponse> rates = courierAnalyticsService
                .getCourierSuccessRate(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(rates);
    }

    /**
     * Retrieves the average rating received by each courier within the
     * specified date range.
     *
     * @param dateRange the date range for the report
     * @return list of average rating responses
     */
    @Operation(summary = "Get average courier ratings", description = "Fetches "
            + " the average rating received by each courier")
    @GetMapping("/average-rating")
    public ResponseEntity<List<CourierAverageRatingResponse>> getAverageRating(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        List<CourierAverageRatingResponse> ratings = courierAnalyticsService
                .getCourierAverageRating(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(ratings);
    }

    /**
     * Retrieves a detailed performance report for each courier including
     * deliveries, ratings, and success rate.
     *
     * @param dateRange the date range for the report
     * @return list of performance report responses
     */
    @Operation(summary = "Get courier performance report", description = ""
            + "Returns a detailed performance report of each courier")
    @GetMapping("/performance-report")
    public ResponseEntity<List<CourierPerformanceReportResponse>> getReport(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        List<CourierPerformanceReportResponse> report = courierAnalyticsService
                .getCourierPerformanceReport(dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(report);
    }
}
