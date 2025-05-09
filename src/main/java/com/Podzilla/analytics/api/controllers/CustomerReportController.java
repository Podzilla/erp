package com.Podzilla.analytics.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Podzilla.analytics.services.CustomerAnalyticsService;
import com.Podzilla.analytics.api.dtos.DateRangePaginationRequest;
import com.Podzilla.analytics.api.dtos.customer.CustomersTopSpendersResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Customer Reports", description = "APIs for customer analytics and"
        + "reporting")
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerReportController {
    private final CustomerAnalyticsService customerAnalyticsService;

    @Operation(summary = "Get top spending customers", description = "Returns"
            + "a paginated list of customers who have spent"
            + "the most money within the specified date range")
    @GetMapping("/top-spenders")
    public List<CustomersTopSpendersResponse> getTopSpenders(
            @Valid @ModelAttribute final DateRangePaginationRequest request) {
        return customerAnalyticsService.getTopSpenders(
                request.getStartDate(),
                request.getEndDate(),
                request.getPage(),
                request.getSize());
    }
}
