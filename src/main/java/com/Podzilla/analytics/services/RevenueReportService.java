package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.RevenueSummaryRequest;
import com.Podzilla.analytics.api.dtos.RevenueSummaryResponse;
import com.Podzilla.analytics.api.projections.RevenueByCategoryProjection;
import com.Podzilla.analytics.api.projections.RevenueSummaryProjection;
import com.Podzilla.analytics.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RevenueReportService {

    private final OrderRepository orderRepository;

    public List<RevenueSummaryResponse> getRevenueSummary(final RevenueSummaryRequest request) {

        final LocalDate startDate = request.getStartDate();
        final LocalDate endDate = request.getEndDate();
        final String periodString = request.getPeriod().name();

        final List<RevenueSummaryProjection> revenueData =
                orderRepository.findRevenueSummaryByPeriod(startDate, endDate, periodString);

        final List<RevenueSummaryResponse> summaryList = new ArrayList<>();

        for (RevenueSummaryProjection row : revenueData) { // Corrected: { moved to same line with space
            RevenueSummaryResponse summaryItem = RevenueSummaryResponse.builder()
                    .periodStartDate(row.getPeriod())
                    .totalRevenue(row.getTotalRevenue())
                    .build();

            summaryList.add(summaryItem);
        }

        return summaryList;
    }

    /**
     * Gets completed order revenue summarized by product category for a date range.
     *
     * @param startDate The start date (inclusive).
     * @param endDate The end date (exclusive).
     * @return A list of revenue summaries per category.
     */
    public List<RevenueByCategoryResponse> getRevenueByCategory(final LocalDate startDate, final LocalDate endDate) {

    final List<RevenueByCategoryProjection> queryResults = orderRepository.findRevenueByCategory(startDate, endDate);

        final List<RevenueByCategoryResponse> summaryList = new ArrayList<>();

        // Each row is [category_string, total_revenue_bigdecimal]
        for (RevenueByCategoryProjection row : queryResults) { // Corrected: { moved to same line with space
            RevenueByCategoryResponse summaryItem = RevenueByCategoryResponse.builder()
                    .category(row.getCategory())
                    .totalRevenue(row.getTotalRevenue())
                    .build();

            summaryList.add(summaryItem);
        }

        return summaryList;
    }
}
