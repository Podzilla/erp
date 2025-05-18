package com.Podzilla.analytics.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Podzilla.analytics.api.dtos.revenue.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.revenue.RevenueSummaryRequest;
import com.Podzilla.analytics.api.dtos.revenue.RevenueSummaryResponse;
import com.Podzilla.analytics.api.projections.revenue.RevenueByCategoryProjection;
import com.Podzilla.analytics.api.projections.revenue.RevenueSummaryProjection;
import com.Podzilla.analytics.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
class RevenueReportServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private RevenueReportService revenueReportService;

    @BeforeEach
    void setUp() {
        revenueReportService = new RevenueReportService(orderRepository);
    }

    @Test
    void getRevenueSummary_WithValidData_ShouldReturnCorrectSummary() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        RevenueSummaryRequest request = RevenueSummaryRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .period(RevenueSummaryRequest.Period.MONTHLY)
                .build();

        List<RevenueSummaryProjection> projections = Arrays.asList(
                summaryProjection(LocalDate.of(2025, 1, 1), new BigDecimal("1000.00")),
                summaryProjection(LocalDate.of(2025, 2, 1), new BigDecimal("2000.00")));

        when(orderRepository.findRevenueSummaryByPeriod(eq(startDate.atStartOfDay()), eq(endDate.atTime(LocalTime.MAX)),
                eq("MONTHLY")))
                .thenReturn(projections);

        // Act
        List<RevenueSummaryResponse> result = revenueReportService.getRevenueSummary(request.getStartDate(),
                request.getEndDate(), request.getPeriod().name());

        // Assert
        assertEquals(2, result.size());
        assertEquals(LocalDate.of(2025, 1, 1), result.get(0).getPeriodStartDate());
        assertEquals(new BigDecimal("1000.00"), result.get(0).getTotalRevenue());
        assertEquals(LocalDate.of(2025, 2, 1), result.get(1).getPeriodStartDate());
        assertEquals(new BigDecimal("2000.00"), result.get(1).getTotalRevenue());
    }

    @Test
    void getRevenueSummary_WithEmptyData_ShouldReturnEmptyList() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        RevenueSummaryRequest request = RevenueSummaryRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .period(RevenueSummaryRequest.Period.MONTHLY)
                .build();

        when(orderRepository.findRevenueSummaryByPeriod(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        List<RevenueSummaryResponse> result = revenueReportService.getRevenueSummary(request.getStartDate(),
                request.getEndDate(), request.getPeriod().name());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getRevenueSummary_WithStartDateAfterEndDate_ShouldReturnEmptyList() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 12, 31);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        RevenueSummaryRequest request = RevenueSummaryRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .period(RevenueSummaryRequest.Period.MONTHLY)
                .build();

        when(orderRepository.findRevenueSummaryByPeriod(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        List<RevenueSummaryResponse> result = revenueReportService.getRevenueSummary(request.getStartDate(),
                request.getEndDate(), request.getPeriod().name());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getRevenueByCategory_WithValidData_ShouldReturnCorrectCategories() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        List<RevenueByCategoryProjection> projections = Arrays.asList(
                categoryProjection("Books", new BigDecimal("3000.00")),
                categoryProjection("Electronics", new BigDecimal("5000.00")));

        when(orderRepository.findRevenueByCategory(eq(startDate.atStartOfDay()), eq(endDate.atTime(
                LocalTime.MAX))))
                .thenReturn(projections);// Act
        List<RevenueByCategoryResponse> result = revenueReportService.getRevenueByCategory(startDate, endDate);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Books", result.get(0).getCategory());
        assertEquals(new BigDecimal("3000.00"), result.get(0).getTotalRevenue());
        assertEquals("Electronics", result.get(1).getCategory());
        assertEquals(new BigDecimal("5000.00"), result.get(1).getTotalRevenue());
    }

    @Test
    void getRevenueByCategory_WithEmptyData_ShouldReturnEmptyList() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        when(orderRepository.findRevenueByCategory(any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        List<RevenueByCategoryResponse> result = revenueReportService.getRevenueByCategory(startDate, endDate);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getRevenueByCategory_WithNullRevenue_ShouldHandleGracefully() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        List<RevenueByCategoryProjection> projections = Arrays.asList(
                new RevenueByCategoryProjection() {
                    @Override
                    public String getCategory() {
                        return "Electronics";
                    }

                    @Override
                    public BigDecimal getTotalRevenue() {
                        return null;
                    }
                });

        when(orderRepository.findRevenueByCategory(eq(startDate.atStartOfDay()), eq(endDate.atTime(
                LocalTime.MAX))))
                .thenReturn(projections);

        // Act
        List<RevenueByCategoryResponse> result = revenueReportService.getRevenueByCategory(startDate, endDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getCategory());
        assertNull(result.get(0).getTotalRevenue());
    }

    @Test
    void getRevenueByCategory_WithStartDateAfterEndDate_ShouldReturnEmptyList() {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 12, 31);
        LocalDate endDate = LocalDate.of(2025, 1, 1);

        when(orderRepository.findRevenueByCategory(any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        List<RevenueByCategoryResponse> result = revenueReportService.getRevenueByCategory(startDate, endDate);

        // Assert
        assertTrue(result.isEmpty());
    }

    private RevenueSummaryProjection summaryProjection(LocalDate date, BigDecimal revenue) {
        return new RevenueSummaryProjection() {
            public LocalDate getPeriod() {
                return date;
            }

            public BigDecimal getTotalRevenue() {
                return revenue;
            }
        };
    }

    private RevenueByCategoryProjection categoryProjection(String category, BigDecimal revenue) {
        return new RevenueByCategoryProjection() {
            public String getCategory() {
                return category;
            }

            public BigDecimal getTotalRevenue() {
                return revenue;
            }
        };
    }
}
