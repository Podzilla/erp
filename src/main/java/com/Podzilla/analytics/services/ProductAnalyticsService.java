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
import lombok.extern.slf4j.Slf4j;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.util.StringToUUIDParser;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
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
            final SortBy sortBy) {
        log.info("Getting top sellers between {} and {}"
                + " with limit {} and sortBy {}", startDate,
                endDate, limit, sortBy);

        final String sortByString = sortBy != null ? sortBy.name()
                : SortBy.REVENUE.name();

        final LocalDateTime startDateTime = startDate.atStartOfDay();
        final LocalDateTime endDateTime = endDate
                .plusDays(DAYS_TO_INCLUDE_END_DATE).atStartOfDay();

        final List<TopSellingProductProjection> queryResults = productRepository
                .findTopSellers(startDateTime,
                        endDateTime,
                        limit, sortByString);

        log.debug("Query returned {} top sellers", queryResults.size());

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
            log.debug("Limiting top sellers list to {}", limit);
            topSellersList = topSellersList.subList(SUBLIST_START_INDEX, limit);
        }

        log.info("Returning {} top sellers", topSellersList.size());
        return topSellersList;
    }

    public void saveProduct(
            final String productId,
            final String productName,
            final String productCategory,
            final BigDecimal productCost,
            final Integer productLowStockThreshold) {
        log.info("Saving product with id: {}, name: {}, "
                + " category: {}", productId, productName, productCategory);
        UUID id = StringToUUIDParser.parseStringToUUID(productId);
        Product product = Product.builder()
                .id(id)
                .name(productName)
                .category(productCategory)
                .cost(productCost)
                .lowStockThreshold(productLowStockThreshold)
                .build();
        productRepository.save(product);
        log.debug("Product saved: {}", product);
    }
}
