package com.Podzilla.analytics.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List; // Keep import if TopSellerRequest still uses LocalDate

import static org.junit.jupiter.api.Assertions.assertEquals; // Import LocalDateTime
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Podzilla.analytics.api.dtos.product.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.product.TopSellerResponse;
import com.Podzilla.analytics.api.projections.product.TopSellingProductProjection;
import com.Podzilla.analytics.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductAnalyticsServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductAnalyticsService productAnalyticsService;

    @BeforeEach
    void setUp() {
        productAnalyticsService = new ProductAnalyticsService(productRepository);
    }

    @Test
    void getTopSellers_SortByRevenue_ShouldReturnCorrectList() {
        // Arrange
        // Assuming TopSellerRequest still uses LocalDate for input
        LocalDate requestStartDate = LocalDate.of(2025, 1, 1);
        LocalDate requestEndDate = LocalDate.of(2025, 12, 31);

        TopSellerRequest request = TopSellerRequest.builder()
                .startDate(requestStartDate)
                .endDate(requestEndDate)
                .limit(2) // Ensure limit is set to 2
                .sortBy(TopSellerRequest.SortBy.REVENUE)
                .build();

        // Convert LocalDate from request to LocalDateTime for repository call
        // Start of the start day
        LocalDateTime startDate = requestStartDate.atStartOfDay();
        // Start of the day AFTER the end day to include the whole end day in the query
        LocalDateTime endDate = requestEndDate.plusDays(1).atStartOfDay();

        // Mocking the repository to return 2 projections
        List<TopSellingProductProjection> projections = Arrays.asList(
            createProjection(1L, "iPhone", "Electronics", new BigDecimal("1000.00"), 5L),
            createProjection(2L, "MacBook", "Electronics", new BigDecimal("2000.00"), 2L)
        );

        // Ensure the mock returns the correct results based on the given arguments
        // Use LocalDateTime for the eq() matchers
        when(productRepository.findTopSellers(
            eq(startDate),
            eq(endDate),
            eq(2),
            eq("REVENUE")))
        .thenReturn(projections);

        // Act
        List<TopSellerResponse> result = productAnalyticsService.getTopSellers(request.getStartDate(), request.getEndDate(), request.getLimit(), request.getSortBy());

        // Log the result to help with debugging
        result.forEach(item -> System.out.println("Product ID: " + item.getProductId() + " Revenue: " + item.getValue()));

        // Assert (Ensure the order is correct as per revenue)
        assertEquals(2, result.size(), "Expected 2 products in the list.");
        assertEquals(2L, result.get(0).getProductId());  // MacBook should come first due to higher revenue
        assertEquals("MacBook", result.get(0).getProductName());
        assertEquals("Electronics", result.get(0).getCategory());
        assertEquals(new BigDecimal("2000.00"), result.get(0).getValue());

        assertEquals(1L, result.get(1).getProductId());
        assertEquals("iPhone", result.get(1).getProductName());
        assertEquals("Electronics", result.get(1).getCategory());
        assertEquals(new BigDecimal("1000.00"), result.get(1).getValue());
    }


    @Test
    void getTopSellers_SortByUnits_ShouldReturnCorrectList() {
        // Arrange
        LocalDate requestStartDate = LocalDate.of(2025, 1, 1);
        LocalDate requestEndDate = LocalDate.of(2025, 12, 31);

        TopSellerRequest request = TopSellerRequest.builder()
                .startDate(requestStartDate)
                .endDate(requestEndDate)
                .limit(2)
                .sortBy(TopSellerRequest.SortBy.UNITS)
                .build();

        // Convert LocalDate from request to LocalDateTime for repository call
        LocalDateTime startDate = requestStartDate.atStartOfDay();
        LocalDateTime endDate = requestEndDate.plusDays(1).atStartOfDay();

        List<TopSellingProductProjection> projections = Arrays.asList(
            createProjection(1L, "iPhone", "Electronics", new BigDecimal("1000.00"), 5L),
            createProjection(2L, "MacBook", "Electronics", new BigDecimal("2000.00"), 2L)
        );

        // Use LocalDateTime for the eq() matchers
        when(productRepository.findTopSellers(
            eq(startDate),
            eq(endDate),
            eq(2),
            eq("UNITS")))
        .thenReturn(projections);

        // Act
        List<TopSellerResponse> result = productAnalyticsService.getTopSellers(request.getStartDate(), request.getEndDate(), request.getLimit(), request.getSortBy());

        // Assert (Ensure the order is correct as per units)
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getProductId());  // iPhone comes first because of more units sold
        assertEquals("iPhone", result.get(0).getProductName());
        assertEquals("Electronics", result.get(0).getCategory());
        // Note: The projection returns revenue and units as BigDecimal and Long respectively.
        // The conversion to TopSellerResponse seems to put units into the 'value' field for this case.
        assertEquals(new BigDecimal("5"), result.get(0).getValue());


        assertEquals(2L, result.get(1).getProductId());
        assertEquals("MacBook", result.get(1).getProductName());
        assertEquals("Electronics", result.get(1).getCategory());
        assertEquals(new BigDecimal("2"), result.get(1).getValue());
    }

    @Test
    void getTopSellers_WithEmptyResult_ShouldReturnEmptyList() {
        // Arrange
        LocalDate requestStartDate = LocalDate.of(2025, 1, 1);
        LocalDate requestEndDate = LocalDate.of(2025, 12, 31);

        TopSellerRequest request = TopSellerRequest.builder()
                .startDate(requestStartDate)
                .endDate(requestEndDate)
                .limit(10)
                .sortBy(TopSellerRequest.SortBy.REVENUE)
                .build();

        // Use any() matchers for LocalDateTime parameters
        when(productRepository.findTopSellers(any(LocalDateTime.class), any(LocalDateTime.class), any(), any()))
            .thenReturn(Collections.emptyList());

        // Act
        List<TopSellerResponse> result = productAnalyticsService.getTopSellers(request.getStartDate(), request.getEndDate(), request.getLimit(), request.getSortBy());

        // Assert
        assertTrue(result.isEmpty());
    }


    @Test
    void getTopSellers_WithZeroLimit_ShouldReturnEmptyList() {
        // Arrange
        LocalDate requestStartDate = LocalDate.of(2025, 1, 1);
        LocalDate requestEndDate = LocalDate.of(2025, 12, 31);
        TopSellerRequest request = TopSellerRequest.builder()
                .startDate(requestStartDate)
                .endDate(requestEndDate)
                .limit(0)
                .sortBy(TopSellerRequest.SortBy.REVENUE)
                .build();

        // Convert LocalDate from request to LocalDateTime for repository call
        LocalDateTime startDate = requestStartDate.atStartOfDay();
        LocalDateTime endDate = requestEndDate.plusDays(1).atStartOfDay();


        // Use LocalDateTime for the eq() matchers
        when(productRepository.findTopSellers(
            eq(startDate),
            eq(endDate),
            eq(0),
            eq("REVENUE")))
        .thenReturn(Collections.emptyList());

        // Act
        List<TopSellerResponse> result = productAnalyticsService.getTopSellers(request.getStartDate(), request.getEndDate(), request.getLimit(), request.getSortBy());

        // Assert
        assertTrue(result.isEmpty());
    }

    private TopSellingProductProjection createProjection(
            final Long id,
            final String name,
            final String category,
            final BigDecimal revenue,
            final Long units) {
        return new TopSellingProductProjection() {
            @Override
            public Long getId() {
                return id;
            }
            @Override
            public String getName() {
                return name;
            }
            @Override
            public String getCategory() {
                return category;
            }
            @Override
            public BigDecimal getTotalRevenue() {
                return revenue;
            }
            @Override
            public Long getTotalUnits() {
                return units;
            }
        };
    }
}