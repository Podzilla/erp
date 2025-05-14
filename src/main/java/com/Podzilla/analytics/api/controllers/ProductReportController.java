package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.TopSellerResponse;
import com.Podzilla.analytics.services.ProductAnalyticsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController

@RequestMapping("/product-analytics")
public class ProductReportController {

    private final ProductAnalyticsService productAnalyticsService;

    @GetMapping("/top-sellers")
    public ResponseEntity<List<TopSellerResponse>> getTopSellers(
            @Valid @ModelAttribute final TopSellerRequest requestDTO
    ) {

        List<TopSellerResponse> topSellersList = productAnalyticsService.getTopSellers(requestDTO);

        return ResponseEntity.ok(topSellersList);
    }
}
