package com.Podzilla.analytics.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.revenue.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.revenue.RevenueSummaryResponse;
import com.Podzilla.analytics.api.projections.revenue.RevenueByCategoryProjection;
import com.Podzilla.analytics.api.projections.revenue.RevenueSummaryProjection;
import com.Podzilla.analytics.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RevenueReportService {

    private final OrderRepository orderRepository;

    public List<RevenueSummaryResponse> getRevenueSummary(
            final LocalDate startDate,
            final LocalDate endDate,
            final String periodString) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        log.info("Getting revenue summary between {} and {}"
                + " for period '{}'", startDate, endDate, periodString);

        final List<RevenueSummaryProjection> revenueData = orderRepository
                .findRevenueSummaryByPeriod(
                        startDateTime,
                        endDateTime, periodString);

        final List<RevenueSummaryResponse> summaryList = new ArrayList<>();

        for (RevenueSummaryProjection row : revenueData) {
            RevenueSummaryResponse summaryItem = RevenueSummaryResponse
                    .builder()
                    .periodStartDate(row.getPeriod())
                    .totalRevenue(row.getTotalRevenue())
                    .build();

            summaryList.add(summaryItem);
        }

        log.info("Revenue summary result size: {}", summaryList.size());
        return summaryList;
    }

    /**
     * Gets completed order revenue summarized by product category
     * for a date range.
     *
     * @param startDate The start date (inclusive).
     * @param endDate   The end date (exclusive).
     * @return A list of revenue summaries per category.
     */
    public List<RevenueByCategoryResponse> getRevenueByCategory(
            final LocalDate startDate, final LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        log.info("Getting revenue by category between"
                + " {} and {}", startDate, endDate);

        final List<RevenueByCategoryProjection> queryResults = orderRepository
                .findRevenueByCategory(
                        startDateTime,
                        endDateTime);

        final List<RevenueByCategoryResponse> summaryList = new ArrayList<>();

        // Each row is [category_string, total_revenue_bigdecimal]
        for (RevenueByCategoryProjection row : queryResults) {
            RevenueByCategoryResponse summaryItem = RevenueByCategoryResponse
                    .builder()
                    .category(row.getCategory())
                    .totalRevenue(row.getTotalRevenue())
                    .build();

            summaryList.add(summaryItem);
        }

        log.info("Revenue by category result"
                + " size: {}", summaryList.size());
        return summaryList;
    }
}
