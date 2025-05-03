package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.CourierAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/couriers")
public class CourierReportController {
    private final CourierAnalyticsService courierAnalyticsService;
}