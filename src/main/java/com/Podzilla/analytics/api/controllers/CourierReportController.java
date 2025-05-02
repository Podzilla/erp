package com.Podzilla.analytics.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.DTOs.CourierAverageRatingDTO;
import com.Podzilla.analytics.api.DTOs.CourierDeliveryCountDTO;
import com.Podzilla.analytics.api.DTOs.CourierSuccessRateDTO;
import com.Podzilla.analytics.services.CourierAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/couriers")
public class CourierReportController {
    private final CourierAnalyticsService courierAnalyticsService;

    @GetMapping("/delivery-counts")
    public List<CourierDeliveryCountDTO> getCourierDeliveryCounts(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return courierAnalyticsService.getCourierDeliveryCounts(startDate, endDate);
    }

    @GetMapping("/success-rate")
    public List<CourierSuccessRateDTO> getCourierSuccessRate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return courierAnalyticsService.getCourierSuccessRate(startDate, endDate);
    }

    @GetMapping("/average-rating")
    public List<CourierAverageRatingDTO> getCourierAverageRating(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return courierAnalyticsService.getCourierAverageRating(startDate, endDate);
    }

}