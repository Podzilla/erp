package com.Podzilla.analytics.api.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Podzilla.analytics.api.DTOs.ProfitByCategoryDTO;
import com.Podzilla.analytics.services.ProfitAnalyticsService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("profit")
public class ProfitReportController {
    private final ProfitAnalyticsService profitAnalyticsService;
    
    @GetMapping("/by-category")
    public ResponseEntity<List<ProfitByCategoryDTO>> getProfitByCategory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<ProfitByCategoryDTO> profitData = profitAnalyticsService.getProfitByCategory(startDate, endDate);
        return ResponseEntity.ok(profitData);
    }
}