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

    private static final int DAYS_TO_INCLUDE_END_DATE = 1; // Magic number replacement
    private static final int SUBLIST_START_INDEX = 0; // Magic number replacement

    /**
     * Gets top selling products by revenue or units for a date range.
     *
     * @param request The request DTO containing date range, limit, and sort
     * criteria. // Removed trailing space
     * @return A list of top seller response dtos.
     */
    public List<TopSellerResponse> getTopSellers(final TopSellerRequest request) { // Removed space before )

        final LocalDate startDate = request.getStartDate();
        final LocalDate endDate = request.getEndDate();
        final Integer limit = request.getLimit();
        final SortBy sortBy = request.getSortBy();
        final String sortByString = sortBy != null ? sortBy.name() : SortBy.REVENUE.name();

        final LocalDateTime startDateTime = startDate.atStartOfDay();
        final LocalDateTime endDateTime = endDate.plusDays(DAYS_TO_INCLUDE_END_DATE).atStartOfDay(); // Used constant

        final List<TopSellingProductProjection> queryResults =
                productRepository.findTopSellers(startDateTime, endDateTime, // Added space after comma
                        limit, sortByString); // Added space after comma, removed space before )

        List<TopSellerResponse> topSellersList = new ArrayList<>();

        for (TopSellingProductProjection row : queryResults) {
            BigDecimal value = (sortBy == SortBy.UNITS)
                    ? BigDecimal.valueOf(row.getTotalUnits())
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
            topSellersList = topSellersList.subList(SUBLIST_START_INDEX, limit);
        }

        return topSellersList;
    }
}
