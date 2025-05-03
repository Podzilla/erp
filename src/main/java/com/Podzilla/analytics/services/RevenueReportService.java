package com.Podzilla.analytics.services;

import java.math.BigDecimal; // Import BigDecimal
import java.time.LocalDate; // Import LocalDate
import java.time.ZoneId;   // Might be needed if converting Date to LocalDate later
import java.util.ArrayList; // Import ArrayList
import java.util.Date;      // Import Date (because your Response DTO uses it)
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.RevenueByCategoryResponse;
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

    /**
     * Gets completed order revenue summarized by product category for a date range.
     *
     * @param startDate The start date (inclusive).
     * @param endDate   The end date (exclusive).
     * @return A list of revenue summaries per category.
     */
    public List<RevenueByCategoryResponse> getRevenueByCategory(LocalDate startDate, LocalDate endDate) {

        // Call the repository method to get the raw data grouped by category
        List<Object[]> queryResults = orderRepository.findRevenueByCategory(startDate, endDate);

        // --- Convert List<Object[]> to List<RevenueByCategoryResponse> ---

        // Create a new list to hold the response DTOs
        List<RevenueByCategoryResponse> summaryList = new ArrayList<>();

        // Loop through each Object[] array in the result list
        // Each row is [category_string, total_revenue_bigdecimal]
        for (Object[] row : queryResults) {
            // Extract the data from the Object[] array
            String category = (String) row[0]; // First element is the category (String)
            BigDecimal totalRevenue = (BigDecimal) row[1]; // Second element is the sum (BigDecimal)

            // Create a RevenueByCategoryResponse DTO using the extracted data
            RevenueByCategoryResponse summaryItem = RevenueByCategoryResponse.builder()
                                                       .category(category)
                                                       .totalRevenue(totalRevenue)
                                                       .build();

            // Add the created DTO to the list of summaries
            summaryList.add(summaryItem);
        }

        // Return the list of RevenueByCategoryResponse DTOs
        return summaryList;
    }
}