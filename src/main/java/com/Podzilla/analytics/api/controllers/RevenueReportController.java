package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.revenue.RevenueByCategoryRequest;
import com.Podzilla.analytics.api.dtos.revenue.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.revenue.RevenueSummaryRequest;
import com.Podzilla.analytics.api.dtos.revenue.RevenueSummaryResponse;
import com.Podzilla.analytics.services.RevenueReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/revenue-analytics")
public class RevenueReportController {
    private final RevenueReportService revenueReportService;

    @GetMapping("/summary")
    public ResponseEntity<List<RevenueSummaryResponse>> getRevenueSummary(
            @Valid @ModelAttribute final RevenueSummaryRequest requestDTO) {
        return ResponseEntity.ok(revenueReportService
                .getRevenueSummary(requestDTO.getStartDate(),
                        requestDTO.getEndDate(),
                        requestDTO.getPeriod().name()));
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<RevenueByCategoryResponse>> getRevenueByCategory(
            @Valid @ModelAttribute final RevenueByCategoryRequest requestDTO) {
        List<RevenueByCategoryResponse> summaryList = revenueReportService
                .getRevenueByCategory(
                        requestDTO.getStartDate(),
                        requestDTO.getEndDate());
        return ResponseEntity.ok(summaryList);
    }
}
