package com.Podzilla.analytics.services;

import com.Podzilla.analytics.api.dtos.customer.CustomersTopSpendersResponse;
import com.Podzilla.analytics.api.projections.customer.CustomersTopSpendersProjection;
import com.Podzilla.analytics.repositories.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerReportServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerAnalyticsService customerAnalyticsService;

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

    private CustomersTopSpendersProjection createMockProjection(
            UUID customerId, String customerName, BigDecimal totalSpending) {
        CustomersTopSpendersProjection mockProjection = Mockito.mock(CustomersTopSpendersProjection.class);
        Mockito.lenient().when(mockProjection.getCustomerId()).thenReturn(customerId);
        Mockito.lenient().when(mockProjection.getCustomerName()).thenReturn(customerName);
        Mockito.lenient().when(mockProjection.getTotalSpending()).thenReturn(totalSpending);
        return mockProjection;
    }

    @Test
    void getTopSpenders_shouldReturnCorrectSpendersForMultipleCustomers() {
        // Arrange
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        CustomersTopSpendersProjection janeData = createMockProjection(
                customerId1, "Jane", new BigDecimal("5000.00"));
        CustomersTopSpendersProjection johnData = createMockProjection(
                customerId2, "John", new BigDecimal("3000.00"));

        Page<CustomersTopSpendersProjection> mockPage = new PageImpl<>(
                Arrays.asList(janeData, johnData));

        when(customerRepository.findTopSpenders(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(PageRequest.class)))
                .thenReturn(mockPage);

        // Act
        List<CustomersTopSpendersResponse> result = customerAnalyticsService
                .getTopSpenders(testStartDate, testEndDate, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        CustomersTopSpendersResponse janeResponse = result.stream()
                .filter(r -> r.getCustomerName().equals("Jane"))
                .findFirst().orElse(null);
        assertNotNull(janeResponse);
        assertEquals(customerId1, janeResponse.getCustomerId());
        assertEquals(new BigDecimal("5000.00"), janeResponse.getTotalSpending());

        CustomersTopSpendersResponse johnResponse = result.stream()
                .filter(r -> r.getCustomerName().equals("John"))
                .findFirst().orElse(null);
        assertNotNull(johnResponse);
        assertEquals(customerId2, johnResponse.getCustomerId());
        assertEquals(new BigDecimal("3000.00"), johnResponse.getTotalSpending());

        // Verify repository method was called with correct arguments
        Mockito.verify(customerRepository).findTopSpenders(
                expectedStartDateTime,
                expectedEndDateTime,
                PageRequest.of(0, 10));
    }

    @Test
    void getTopSpenders_shouldReturnEmptyListWhenNoData() {
        // Arrange
        Page<CustomersTopSpendersProjection> emptyPage = new PageImpl<>(Collections.emptyList());
        when(customerRepository.findTopSpenders(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(PageRequest.class)))
                .thenReturn(emptyPage);

        // Act
        List<CustomersTopSpendersResponse> result = customerAnalyticsService
                .getTopSpenders(testStartDate, testEndDate, 0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(customerRepository).findTopSpenders(
                expectedStartDateTime,
                expectedEndDateTime,
                PageRequest.of(0, 10));
    }

    @Test
    void getTopSpenders_shouldHandlePagination() {
        // Arrange
        UUID customerId1 = UUID.randomUUID();
        CustomersTopSpendersProjection janeData = createMockProjection(
                customerId1, "Jane", new BigDecimal("5000.00"));

        Page<CustomersTopSpendersProjection> mockPage = new PageImpl<>(
                Collections.singletonList(janeData));

        when(customerRepository.findTopSpenders(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(PageRequest.class)))
                .thenReturn(mockPage);

        // Act
        List<CustomersTopSpendersResponse> result = customerAnalyticsService
                .getTopSpenders(testStartDate, testEndDate, 0, 1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getCustomerName());
        assertEquals(new BigDecimal("5000.00"), result.get(0).getTotalSpending());

        Mockito.verify(customerRepository).findTopSpenders(
                expectedStartDateTime,
                expectedEndDateTime,
                PageRequest.of(0, 1));
    }
}
