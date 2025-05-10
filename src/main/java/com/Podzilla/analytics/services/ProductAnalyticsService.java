package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.TopSellerRequest.SortBy; // Import SortBy enum
import com.Podzilla.analytics.api.dtos.TopSellerResponse;
import com.Podzilla.analytics.api.projections.TopSellingProductProjection;
import com.Podzilla.analytics.repositories.ProductRepository; // Import ProductRepository

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductAnalyticsService {

    private final ProductRepository productRepository;

    /**
     * Gets top selling products by revenue or units for a date range.
     *
     * @param request The request DTO containing date range, limit, and sort
     *                criteria.
     * @return A list of top seller response dtos.
     */
    public List<TopSellerResponse> getTopSellers(TopSellerRequest request) {

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Integer limit = request.getLimit();
        SortBy sortBy = request.getSortBy();
        String sortByString = sortBy != null ? sortBy.name() : SortBy.REVENUE.name();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay(); // To include the whole end day

        List<TopSellingProductProjection> queryResults = productRepository.findTopSellers(startDateTime, endDateTime,
                limit, sortByString);

        List<TopSellerResponse> topSellersList = new ArrayList<>();

        // Each row is [product_id, product_name, category, total_revenue, total_units]
        for (TopSellingProductProjection row : queryResults) {
            BigDecimal value = (sortBy == SortBy.UNITS) ? BigDecimal.valueOf(row.getTotalUnits())
                    : row.getTotalRevenue();
            TopSellerResponse topSellerItem = TopSellerResponse.builder()
                    .productId(row.getId())
                    .productName(row.getName())
                    .category(row.getCategory())
                    .value(value)
                    .build();

            topSellersList.add(topSellerItem);
        }
        topSellersList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        if (limit != null && limit > 0 && limit < topSellersList.size()) {
            topSellersList = topSellersList.subList(0, limit);
        }

        return topSellersList;
    }
}