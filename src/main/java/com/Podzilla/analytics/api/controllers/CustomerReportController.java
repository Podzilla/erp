package com.Podzilla.analytics.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.CustomerAnalyticsService;


import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("customers")
public class CustomerReportController {
    private final CustomerAnalyticsService customerAnalyticsService;

    public CustomerReportController(CustomerAnalyticsService customerAnalyticsService) {
        this.customerAnalyticsService = customerAnalyticsService;
    }

    @GetMapping("/top-spenders")
    public Page<Map<String, Object>> getTopSpenders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return customerAnalyticsService.getTopSpenders(startDate, endDate, page, size);
    }
}