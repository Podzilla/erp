package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.product.TopSellerRequest.SortBy;
import com.Podzilla.analytics.api.projections.product.TopSellingProductProjection;
import com.Podzilla.analytics.api.dtos.product.TopSellerResponse;
import com.Podzilla.analytics.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductAnalyticsService {

    private final ProductRepository productRepository;

    private static final int DAYS_TO_INCLUDE_END_DATE = 1;
    private static final int SUBLIST_START_INDEX = 0;

    /**
     * Retrieves the top-selling products within a specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate   the end date of the range
     * @param limit     the maximum number of results to return
     * @param sortBy    the sorting criteria (units sold or revenue)
     * @return a list of top-selling products
     */
    public List<TopSellerResponse> getTopSellers(
            final LocalDate startDate,
            final LocalDate endDate,
            final Integer limit,
            final SortBy sortBy
) {

        final String sortByString = sortBy != null ? sortBy.name()
                : SortBy.REVENUE.name();

        final LocalDateTime startDateTime = startDate.atStartOfDay();
        final LocalDateTime endDateTime = endDate
                .plusDays(DAYS_TO_INCLUDE_END_DATE).atStartOfDay();

        final List<TopSellingProductProjection> queryResults = productRepository
                .findTopSellers(startDateTime,
                        endDateTime,
                        limit, sortByString);

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
