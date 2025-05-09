package com.Podzilla.analytics.api.controllers;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.dtos.CustomersTopSpendersDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Podzilla.analytics.services.CustomerAnalyticsService;


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerReportController {
    private final CustomerAnalyticsService customerAnalyticsService;

    @GetMapping("/top-spenders")
    public List<CustomersTopSpendersDTO> getTopSpenders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return customerAnalyticsService.getTopSpenders(startDate, endDate, page, size);
    }
}
