package com.Podzilla.analytics.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.dtos.CourierAverageRatingDTO;
import com.Podzilla.analytics.api.dtos.CourierDeliveryCountDTO;
import com.Podzilla.analytics.api.dtos.CourierPerformanceReportDTO;
import com.Podzilla.analytics.api.dtos.CourierSuccessRateDTO;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/couriers")
public class CourierReportController {
    private final CourierAnalyticsService courierAnalyticsService;

    @GetMapping("/delivery-counts")
    public ResponseEntity<List<CourierDeliveryCountDTO>> getCourierDeliveryCounts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<CourierDeliveryCountDTO> counts = courierAnalyticsService.getCourierDeliveryCounts(startDate, endDate);
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/success-rate")
    public ResponseEntity<List<CourierSuccessRateDTO>> getCourierSuccessRate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<CourierSuccessRateDTO> rates = courierAnalyticsService.getCourierSuccessRate(startDate, endDate);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/average-rating")
    public ResponseEntity<List<CourierAverageRatingDTO>> getCourierAverageRating(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<CourierAverageRatingDTO> ratings = courierAnalyticsService.getCourierAverageRating(startDate, endDate);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/performance-report")
    public ResponseEntity<List<CourierPerformanceReportDTO>> getCourierPerformanceReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<CourierPerformanceReportDTO> report = courierAnalyticsService.getCourierPerformanceReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

}