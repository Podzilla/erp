package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Podzilla.analytics.services.OrderAnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import java.util.List;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.api.dtos.order.OrderFailureResponse;
import com.Podzilla.analytics.api.dtos.order.OrderRegionResponse;
import com.Podzilla.analytics.api.dtos.order.OrderStatusResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-analytics")
public class OrderReportController {
    private final OrderAnalyticsService orderAnalyticsService;

    @Operation(summary = "Get order counts and revenue by region",
     description = "Returns the total number of orders"
            + "placed in each region and their corresponding average revenue")
    @GetMapping("/by-region")
    public ResponseEntity<List<OrderRegionResponse>> getOrdersByRegion(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /order-analytics/by-region"
                + " with attributes: {}", dateRange);
        List<OrderRegionResponse> ordersByRegion = orderAnalyticsService
                .getOrdersByRegion(
                        dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(ordersByRegion);
    }

    @Operation(summary = "Get order status counts",
     description = "Returns the total number of orders"
            + "in each status (e.g., COMPLETED, SHIPPED, FAILED)")
    @GetMapping("/status-counts")
    public ResponseEntity<List<OrderStatusResponse>> getOrdersStatusCounts(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /order-analytics/status-counts"
                + " with attributes: {}", dateRange);
        List<OrderStatusResponse> orderStatusCounts = orderAnalyticsService
                .getOrdersStatusCounts(
                        dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(orderStatusCounts);
    }

    @Operation(summary = "Get order failures",
     description = "Returns the percentage of failed orders"
            + "and a list of the failure reasons"
            + "with their corresponding frequency")
    @GetMapping("/failures")
    public ResponseEntity<OrderFailureResponse> getOrdersFailures(
            @Valid @ModelAttribute final DateRangeRequest dateRange) {
        log.info("Request on: /order-analytics/failures"
                + " with attributes: {}", dateRange);
        OrderFailureResponse orderFailures = orderAnalyticsService
                .getOrdersFailures(
                        dateRange.getStartDate(),
                        dateRange.getEndDate());
        return ResponseEntity.ok(orderFailures);
    }
}
