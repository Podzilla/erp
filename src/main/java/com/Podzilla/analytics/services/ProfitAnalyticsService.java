package com.Podzilla.analytics.services;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.profit.ProfitByCategory;
import com.Podzilla.analytics.api.projections.profit.ProfitByCategoryProjection;
import com.Podzilla.analytics.repositories.OrderItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProfitAnalyticsService {
    private final OrderItemRepository salesLineItemRepository;
    private static final int PERCENTAGE_PRECISION = 4;

    public List<ProfitByCategory> getProfitByCategory(
            final LocalDate startDate,
            final LocalDate endDate) {
        log.info("Getting profit by category between {} and {}",
                startDate, endDate);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<ProfitByCategoryProjection> salesData = salesLineItemRepository
                .findSalesByCategoryBetweenDates(startDateTime, endDateTime);

        log.debug("Fetched {} sales data records for categories",
                salesData.size());

        return salesData.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProfitByCategory convertToDTO(
            final ProfitByCategoryProjection projection) {
        BigDecimal totalRevenue = projection.getTotalRevenue();
        BigDecimal totalCost = projection.getTotalCost();
        BigDecimal grossProfit = totalRevenue.subtract(totalCost);

        BigDecimal grossProfitMargin = BigDecimal.ZERO;
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            grossProfitMargin = grossProfit
                    .divide(totalRevenue, PERCENTAGE_PRECISION,
                            RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        log.debug("Category: {}, Revenue: {}, Cost: {},"
                + " Gross Profit: {}, Margin: {}",
                projection.getCategory(), totalRevenue, totalCost,
                grossProfit, grossProfitMargin);

        return ProfitByCategory.builder()
                .category(projection.getCategory())
                .totalRevenue(totalRevenue)
                .totalCost(totalCost)
                .grossProfit(grossProfit)
                .grossProfitMargin(grossProfitMargin)
                .build();
    }
}
