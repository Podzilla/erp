package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Podzilla.analytics.services.OrderAnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

import com.Podzilla.analytics.api.dtos.order.OrderFailureDTO;
import com.Podzilla.analytics.api.dtos.order.OrderRegionDTO;
import com.Podzilla.analytics.api.dtos.order.OrderStatusDTO;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderReportController {
    private final OrderAnalyticsService orderAnalyticsService;

    @GetMapping("/by-region")
    public ResponseEntity<List<OrderRegionDTO>> getOrdersByRegion(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate startDate,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate endDate) {
        List<OrderRegionDTO> ordersByRegion =
            orderAnalyticsService.getOrdersByRegion(startDate, endDate);
        return ResponseEntity.ok(ordersByRegion);
    }

    @GetMapping("/status-counts")
    public ResponseEntity<List<OrderStatusDTO>> getOrdersStatusCounts(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate startDate,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate endDate
    ) {
        List<OrderStatusDTO> orderStatusCounts =
            orderAnalyticsService.getOrdersStatusCounts(startDate, endDate);
        return ResponseEntity.ok(orderStatusCounts);
    }

    @GetMapping("/failures")
    public ResponseEntity<OrderFailureDTO> getOrdersFailures(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate startDate,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        final LocalDate endDate
    ) {
        OrderFailureDTO orderFailures =
            orderAnalyticsService.getOrdersFailures(startDate, endDate);
        return ResponseEntity.ok(orderFailures);
    }
}
