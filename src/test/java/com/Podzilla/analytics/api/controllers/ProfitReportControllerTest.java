package com.Podzilla.analytics.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.Podzilla.analytics.api.dtos.profit.ProfitByCategory;
import com.Podzilla.analytics.services.ProfitAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfitReportControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ProfitAnalyticsService mockService;

    private ObjectMapper objectMapper;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ProfitByCategory> profitData;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 1, 31);

        // Setup test data
        profitData = Arrays.asList(
                ProfitByCategory.builder()
                        .category("Electronics")
                        .totalRevenue(BigDecimal.valueOf(10000.50))
                        .totalCost(BigDecimal.valueOf(7500.25))
                        .grossProfit(BigDecimal.valueOf(2500.25))
                        .grossProfitMargin(BigDecimal.valueOf(25.00))
                        .build(),
                ProfitByCategory.builder()
                        .category("Clothing")
                        .totalRevenue(BigDecimal.valueOf(5500.75))
                        .totalCost(BigDecimal.valueOf(3000.50))
                        .grossProfit(BigDecimal.valueOf(2500.25))
                        .grossProfitMargin(BigDecimal.valueOf(45.45))
                        .build());
    }

    @Test
    public void testGetProfitByCategory_Success() {
        // Configure mock service
        when(mockService.getProfitByCategory(startDate, endDate))
                .thenReturn(profitData);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<ProfitByCategory>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProfitByCategory>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getCategory()).isEqualTo("Electronics");
        assertThat(response.getBody().get(0).getTotalRevenue()).isEqualTo(BigDecimal.valueOf(10000.50));
        assertThat(response.getBody().get(0).getGrossProfit()).isEqualTo(BigDecimal.valueOf(2500.25));
        assertThat(response.getBody().get(1).getCategory()).isEqualTo("Clothing");
        assertThat(response.getBody().get(1).getGrossProfitMargin()).isEqualTo(BigDecimal.valueOf(45.45));
    }

    @Test
    public void testGetProfitByCategory_EmptyResult() {
        // Configure mock service to return empty list
        when(mockService.getProfitByCategory(startDate, endDate))
                .thenReturn(Collections.emptyList());

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<ProfitByCategory>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProfitByCategory>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void testGetProfitByCategory_MissingStartDate() {
        // Build URL with missing required parameter
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("endDate", endDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetProfitByCategory_MissingEndDate() {
        // Build URL with missing required parameter
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", startDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetProfitByCategory_InvalidDateFormat() {
        // Build URL with invalid date format
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", "2024-01-01")
                .queryParam("endDate", "invalid-date")
                .toUriString();

        // Execute request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetProfitByCategory_StartDateAfterEndDate() {
        // Set up dates where start is after end
        LocalDate invalidStart = LocalDate.of(2024, 2, 1);
        LocalDate invalidEnd = LocalDate.of(2024, 1, 1);

        // Build URL with invalid date range
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", invalidStart.toString())
                .queryParam("endDate", invalidEnd.toString())
                .toUriString();

        // Execute request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetProfitByCategory_FutureDateRange() {
        // Set up future dates
        LocalDate futureStart = LocalDate.now().plusDays(1);
        LocalDate futureEnd = LocalDate.now().plusDays(30);

        // Configure mock service - should return empty data for future dates
        when(mockService.getProfitByCategory(futureStart, futureEnd))
                .thenReturn(Collections.emptyList());

        // Build URL with future date range
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", futureStart.toString())
                .queryParam("endDate", futureEnd.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<ProfitByCategory>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProfitByCategory>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void testGetProfitByCategory_SameDayRange() {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getProfitByCategory(sameDate, sameDate))
                .thenReturn(profitData);

        // Build URL with same day for start and end
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", sameDate.toString())
                .queryParam("endDate", sameDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<ProfitByCategory>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProfitByCategory>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    public void testGetProfitByCategory_ServiceException() {
        // Configure mock service to throw exception
        when(mockService.getProfitByCategory(any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/profit/by-category")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .toUriString();

        // Execute request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 