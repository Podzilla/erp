package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.services.RevenueReportService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/revenue")
public class RevenueReportController {
    private final RevenueReportService revenueReportService;
}