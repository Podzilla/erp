package com.Podzilla.analytics.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Podzilla.analytics.api.dtos.product.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.product.TopSellerResponse;
import com.Podzilla.analytics.services.ProductAnalyticsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/product-analytics")
public class ProductReportController {

    private final ProductAnalyticsService productAnalyticsService;

    @GetMapping("/top-sellers")
    public ResponseEntity<List<TopSellerResponse>> getTopSellers(
            @Valid @ModelAttribute final TopSellerRequest requestDTO) {

        log.info("Request on: /product-analytics/top-sellers"
                + " with attributes: {}", requestDTO);

        List<TopSellerResponse> topSellersList = productAnalyticsService
                .getTopSellers(requestDTO.getStartDate(),
                        requestDTO.getEndDate(),
                        requestDTO.getLimit(),
                        requestDTO.getSortBy());

        return ResponseEntity.ok(topSellersList);
    }
}
