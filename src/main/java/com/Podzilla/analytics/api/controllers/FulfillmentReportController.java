package com.Podzilla.analytics.api.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.FulfillmentAnalyticsService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("fulfillment")
public class FulfillmentReportController {
    private final FulfillmentAnalyticsService fulfillmentAnalyticsService;

    @GetMapping("/place-to-ship-time")
    public ResponseEntity<List<Map<String, Object>>> getPlaceToShipTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam FulfillmentAnalyticsService.PlaceToShipGroupBy groupBy) {

        List<Map<String, Object>> reportData = fulfillmentAnalyticsService.getAveragePlaceToShipTime(startDate, endDate, groupBy);
        return ResponseEntity.ok(reportData);
    }

    @GetMapping("/ship-to-deliver-time")
    public ResponseEntity<List<Map<String, Object>>> getShipToDeliverTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam FulfillmentAnalyticsService.ShipToDeliverGroupBy groupBy) {

        List<Map<String, Object>> reportData = fulfillmentAnalyticsService.getAverageShipToDeliverTime(startDate, endDate, groupBy);
        return ResponseEntity.ok(reportData);
    }
}