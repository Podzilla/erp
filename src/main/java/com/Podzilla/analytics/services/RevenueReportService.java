package com.Podzilla.analytics.services;

import java.math.BigDecimal; // Import BigDecimal
import java.time.LocalDate; // Import LocalDate
import java.time.ZoneId;   // Might be needed if converting Date to LocalDate later
import java.util.ArrayList; // Import ArrayList
import java.util.Date;      // Import Date (because your Response DTO uses it)
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.RevenueSummaryRequest;
import com.Podzilla.analytics.api.DTOs.RevenueSummaryResponse;
import com.Podzilla.analytics.repositories.OrderRepository; // Import the repository

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // Correct for injecting final fields
@Service
public class RevenueReportService {

    // Inject the repository instance using constructor injection (via @RequiredArgsConstructor)
    private final OrderRepository orderRepository;

    // Corrected method signature: public access modifier and returns List<RevenueSummaryResponse>
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
                                                    .period_start_date(periodStartDate)
                                                    .total_revenue(totalRevenue)
                                                    .build();

            summaryList.add(summaryItem);
        }

        return summaryList;
    }
}