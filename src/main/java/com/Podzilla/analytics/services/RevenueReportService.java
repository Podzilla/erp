package com.Podzilla.analytics.services;

import java.math.BigDecimal; 
import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.DTOs.RevenueSummaryRequest;
import com.Podzilla.analytics.api.DTOs.RevenueSummaryResponse;
import com.Podzilla.analytics.repositories.OrderRepository; 

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RevenueReportService {

    private final OrderRepository orderRepository;

    public List<RevenueSummaryResponse> getRevenueSummary(RevenueSummaryRequest request) {

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        String periodString = request.getPeriod().name();

        List<Object[]> revenueData = orderRepository.findRevenueSummaryByPeriod(startDate, endDate, periodString);


        List<RevenueSummaryResponse> summaryList = new ArrayList<>();

        for (Object[] row : revenueData) {
            LocalDate periodStartDate = (LocalDate) row[0];
            BigDecimal totalRevenue = (BigDecimal) row[1];

            RevenueSummaryResponse summaryItem = RevenueSummaryResponse.builder()
                                                    .periodStartDate(periodStartDate)
                                                    .totalRevenue(totalRevenue)
                                                    .build();

            summaryList.add(summaryItem);
        }

        return summaryList;
    }

    /**
     * Gets completed order revenue summarized by product category for a date range.
     *
     * @param startDate The start date (inclusive).
     * @param endDate   The end date (exclusive).
     * @return A list of revenue summaries per category.
     */
    public List<RevenueByCategoryResponse> getRevenueByCategory(LocalDate startDate, LocalDate endDate) {

        List<Object[]> queryResults = orderRepository.findRevenueByCategory(startDate, endDate);


        List<RevenueByCategoryResponse> summaryList = new ArrayList<>();

        // Each row is [category_string, total_revenue_bigdecimal]
        for (Object[] row : queryResults) {
            String category = (String) row[0]; 
            BigDecimal totalRevenue = (BigDecimal) row[1]; 
            RevenueByCategoryResponse summaryItem = RevenueByCategoryResponse.builder()
                                                       .category(category)
                                                       .totalRevenue(totalRevenue)
                                                       .build();

            summaryList.add(summaryItem);
        }

        return summaryList;
    }
}

