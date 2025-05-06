package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.services.RevenueReportService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for handling revenue analytics reports.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/revenue")
public class RevenueReportController {
    /** Service for revenue analytics operations. */
    private final RevenueReportService revenueReportService;
}
