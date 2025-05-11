package com.Podzilla.analytics.services;

import com.Podzilla.analytics.api.dtos.courier.CourierAverageRatingResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierDeliveryCountResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierPerformanceReportResponse;
import com.Podzilla.analytics.api.dtos.courier.CourierSuccessRateResponse;
import com.Podzilla.analytics.api.projections.CourierPerformanceProjection;
import com.Podzilla.analytics.repositories.CourierRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierAnalyticsServiceTest {

    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    private CourierAnalyticsService courierAnalyticsService;

    private LocalDate testStartDate;
    private LocalDate testEndDate;
    private LocalDateTime expectedStartDateTime;
    private LocalDateTime expectedEndDateTime;

    @BeforeEach
    void setUp() {
        testStartDate = LocalDate.of(2024, 1, 1);
        testEndDate = LocalDate.of(2024, 1, 31);
        expectedStartDateTime = testStartDate.atStartOfDay();
        expectedEndDateTime = testEndDate.atTime(LocalTime.MAX);
    }

    private CourierPerformanceProjection createMockProjection(
            Long courierId, String courierName, Long deliveryCount, Long completedCount, BigDecimal averageRating) {
        CourierPerformanceProjection mockProjection = Mockito.mock(CourierPerformanceProjection.class);
        Mockito.lenient().when(mockProjection.getCourierId()).thenReturn(courierId);
        Mockito.lenient().when(mockProjection.getCourierName()).thenReturn(courierName);
        Mockito.lenient().when(mockProjection.getDeliveryCount()).thenReturn(deliveryCount);
        Mockito.lenient().when(mockProjection.getCompletedCount()).thenReturn(completedCount);
        Mockito.lenient().when(mockProjection.getAverageRating()).thenReturn(averageRating);
        return mockProjection;
    }

    @Test
    void getCourierDeliveryCounts_shouldReturnCorrectCountsForMultipleCouriers() {
        // Arrange
        CourierPerformanceProjection janeData = createMockProjection(
                1L, "Jane", 10L, 8L, new BigDecimal("4.5"));
        CourierPerformanceProjection johnData = createMockProjection(
                2L, "John", 5L, 5L, new BigDecimal("4.0"));

        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(janeData, johnData));

        // Act
        List<CourierDeliveryCountResponse> result = courierAnalyticsService
                .getCourierDeliveryCounts(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        CourierDeliveryCountResponse janeResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Jane"))
                .findFirst().orElse(null);
        assertNotNull(janeResponse);
        assertEquals(1L, janeResponse.getCourierId());
        assertEquals(10, janeResponse.getDeliveryCount());

        CourierDeliveryCountResponse johnResponse = result.stream()
                .filter(r -> r.getCourierName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnResponse);
        assertEquals(2L, johnResponse.getCourierId());
        assertEquals(5, johnResponse.getDeliveryCount());

        // Verify repository method was called with correct LocalDateTime arguments
        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierDeliveryCounts_shouldReturnEmptyListWhenNoData() {
        // Arrange
        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<CourierDeliveryCountResponse> result = courierAnalyticsService
                .getCourierDeliveryCounts(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierSuccessRate_shouldReturnCorrectRates() {
        // Arrange
        // Jane: 8 completed out of 10 deliveries = 80%
        CourierPerformanceProjection janeData = createMockProjection(
                1L, "Jane", 10L, 8L, new BigDecimal("4.5"));
        // John: 5 completed out of 5 deliveries = 100%
        CourierPerformanceProjection johnData = createMockProjection(
                2L, "John", 5L, 5L, new BigDecimal("4.0"));
        // Peter: 0 completed out of 2 deliveries = 0%
        CourierPerformanceProjection peterData = createMockProjection(
                3L, "Peter", 2L, 0L, new BigDecimal("3.0"));

        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(janeData, johnData, peterData));

        // Act
        List<CourierSuccessRateResponse> result = courierAnalyticsService
                .getCourierSuccessRate(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        CourierSuccessRateResponse janeResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Jane"))
                .findFirst().orElse(null);
        assertNotNull(janeResponse);
        assertEquals(1L, janeResponse.getCourierId());
        assertTrue(janeResponse.getSuccessRate().compareTo(new BigDecimal("0.80")) == 0);

        CourierSuccessRateResponse johnResponse = result.stream()
                .filter(r -> r.getCourierName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnResponse);
        assertEquals(2L, johnResponse.getCourierId());
        assertTrue(johnResponse.getSuccessRate().compareTo(new BigDecimal("1.00")) == 0);

        CourierSuccessRateResponse peterResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Peter"))
                .findFirst().orElse(null);
        assertNotNull(peterResponse);
        assertEquals(3L, peterResponse.getCourierId());
        assertTrue(peterResponse.getSuccessRate().compareTo(new BigDecimal("0.00")) == 0);

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierSuccessRate_shouldHandleZeroDeliveryCountGracefully() {
        // Arrange
        // Mark: 0 completed out of 0 deliveries. MetricCalculator should handle this
        // (e.g., return 0 or null)
        CourierPerformanceProjection markData = createMockProjection(
                4L, "Mark", 0L, 0L, new BigDecimal("0.0"));

        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(markData));

        // Act
        List<CourierSuccessRateResponse> result = courierAnalyticsService
                .getCourierSuccessRate(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        CourierSuccessRateResponse markResponse = result.get(0);
        assertEquals(4L, markResponse.getCourierId());
        assertTrue(markResponse.getSuccessRate().compareTo(new BigDecimal("0.00")) == 0);

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierSuccessRate_shouldReturnEmptyListWhenNoData() {
        // Arrange
        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<CourierSuccessRateResponse> result = courierAnalyticsService
                .getCourierSuccessRate(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierAverageRating_shouldReturnCorrectAverageRatings() {
        // Arrange
        CourierPerformanceProjection janeData = createMockProjection(
                1L, "Jane", 10L, 8L, new BigDecimal("4.5"));
        CourierPerformanceProjection johnData = createMockProjection(
                2L, "John", 5L, 5L, new BigDecimal("4.0"));
        // Peter: No rating available or 0.0 rating (depends on projection and database)
        CourierPerformanceProjection peterData = createMockProjection(
                3L, "Peter", 2L, 0L, null); // Assuming null for no rating

        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(janeData, johnData, peterData));

        // Act
        List<CourierAverageRatingResponse> result = courierAnalyticsService
                .getCourierAverageRating(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        CourierAverageRatingResponse janeResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Jane"))
                .findFirst().orElse(null);
        assertNotNull(janeResponse);
        assertEquals(1L, janeResponse.getCourierId());
        assertEquals(new BigDecimal("4.5"), janeResponse.getAverageRating());

        CourierAverageRatingResponse johnResponse = result.stream()
                .filter(r -> r.getCourierName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnResponse);
        assertEquals(2L, johnResponse.getCourierId());
        assertEquals(new BigDecimal("4.0"), johnResponse.getAverageRating());

        CourierAverageRatingResponse peterResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Peter"))
                .findFirst().orElse(null);
        assertNotNull(peterResponse);
        assertNull(peterResponse.getAverageRating());
        
        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierAverageRating_shouldReturnEmptyListWhenNoData() {
        // Arrange
        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<CourierAverageRatingResponse> result = courierAnalyticsService
                .getCourierAverageRating(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierPerformanceReport_shouldReturnComprehensiveReport() {
        // Arrange
        // Jane: 8 completed out of 10 deliveries = 80%, Avg Rating 4.5
        CourierPerformanceProjection janeData = createMockProjection(
                1L, "Jane", 10L, 8L, new BigDecimal("4.5"));
        // John: 5 completed out of 5 deliveries = 100%, Avg Rating 4.0
        CourierPerformanceProjection johnData = createMockProjection(
                2L, "John", 5L, 5L, new BigDecimal("4.0"));

        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(janeData, johnData));

        // Act
        List<CourierPerformanceReportResponse> result = courierAnalyticsService
                .getCourierPerformanceReport(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        CourierPerformanceReportResponse janeResponse = result.stream()
                .filter(r -> r.getCourierName().equals("Jane"))
                .findFirst().orElse(null);
        assertNotNull(janeResponse);
        assertEquals(1L, janeResponse.getCourierId());
        assertEquals(10, janeResponse.getDeliveryCount());
        assertTrue(janeResponse.getSuccessRate().compareTo(new BigDecimal("0.80")) == 0);
        assertTrue(janeResponse.getAverageRating().compareTo(new BigDecimal("4.5")) == 0);

        CourierPerformanceReportResponse johnResponse = result.stream()
                .filter(r -> r.getCourierName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnResponse);
        assertEquals(2L, johnResponse.getCourierId());
        assertEquals(5, johnResponse.getDeliveryCount());
        assertTrue(johnResponse.getSuccessRate().compareTo(new BigDecimal("1.00")) == 0);
        assertTrue(johnResponse.getAverageRating().compareTo(new BigDecimal("4.0")) == 0);

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }

    @Test
    void getCourierPerformanceReport_shouldReturnEmptyListWhenNoData() {
        // Arrange
        when(courierRepository.findCourierPerformanceBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        List<CourierPerformanceReportResponse> result = courierAnalyticsService
                .getCourierPerformanceReport(testStartDate, testEndDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(courierRepository).findCourierPerformanceBetweenDates(
                expectedStartDateTime, expectedEndDateTime);
    }
}