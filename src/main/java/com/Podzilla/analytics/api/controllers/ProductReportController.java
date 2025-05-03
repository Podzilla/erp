package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.ProductAnalyticsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductReportController {
    private final ProductAnalyticsService productAnalyticsService;

}
