package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.DTOs.TopSellerRequest;
import com.Podzilla.analytics.api.DTOs.TopSellerRequest.SortBy; // Import SortBy enum
import com.Podzilla.analytics.api.DTOs.TopSellerResponse;
import com.Podzilla.analytics.repositories.ProductRepository; // Import ProductRepository

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductAnalyticsService {

    private final ProductRepository productRepository;

    /**
     * Gets top selling products by revenue or units for a date range.
     *
     * @param request The request DTO containing date range, limit, and sort criteria.
     * @return A list of top seller response DTOs.
     */
    public List<TopSellerResponse> getTopSellers(TopSellerRequest request) {

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Integer limit = request.getLimit();
        SortBy sortBy = request.getSortBy();
        String sortByString = sortBy != null ? sortBy.name() : SortBy.REVENUE.name(); // Get enum name as String, default to REVENUE

        List<Object[]> queryResults = productRepository.findTopSellers(startDate, endDate, limit, sortByString);


        List<TopSellerResponse> topSellersList = new ArrayList<>();

        // Each row is [product_id, product_name, category, total_revenue, total_units]
        for (Object[] row : queryResults) {
            Long productId = (Long) row[0];
            String productName = (String) row[1];
            String category = (String) row[2];
            BigDecimal totalRevenue = (BigDecimal) row[3]; 
            BigDecimal totalUnits = (BigDecimal) row[4];  
            BigDecimal value = (sortBy == SortBy.UNITS) ? totalUnits : totalRevenue; 
            TopSellerResponse topSellerItem = TopSellerResponse.builder()
                                                .productId(productId)
                                                .productName(productName)
                                                .category(category)
                                                .value(value) 
                                                .build();

            topSellersList.add(topSellerItem);
        }

        return topSellersList;
    }
}