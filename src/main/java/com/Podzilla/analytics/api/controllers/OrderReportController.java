package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.OrderAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderReportController {
    private final OrderAnalyticsService orderAnalyticsService;
}
