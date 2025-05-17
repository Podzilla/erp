package com.Podzilla.analytics.controllers;

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

import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentPlaceToShipRequest.PlaceToShipGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentShipToDeliverRequest.ShipToDeliverGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.services.FulfillmentAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FulfillmentReportControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private FulfillmentAnalyticsService mockService;

    private ObjectMapper objectMapper;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<FulfillmentTimeResponse> overallTimeResponses;
    private List<FulfillmentTimeResponse> regionTimeResponses;
    private List<FulfillmentTimeResponse> courierTimeResponses;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 1, 31);

        // Setup test data
        overallTimeResponses = Arrays.asList(
                FulfillmentTimeResponse.builder()
                        .groupByValue("OVERALL")
                        .averageDuration(BigDecimal.valueOf(24.5))
                        .build());

        regionTimeResponses = Arrays.asList(
                FulfillmentTimeResponse.builder()
                        .groupByValue("RegionID_1")
                        .averageDuration(BigDecimal.valueOf(20.2))
                        .build(),
                FulfillmentTimeResponse.builder()
                        .groupByValue("RegionID_2")
                        .averageDuration(BigDecimal.valueOf(28.7))
                        .build());

        courierTimeResponses = Arrays.asList(
                FulfillmentTimeResponse.builder()
                        .groupByValue("CourierID_1")
                        .averageDuration(BigDecimal.valueOf(18.3))
                        .build(),
                FulfillmentTimeResponse.builder()
                        .groupByValue("CourierID_2")
                        .averageDuration(BigDecimal.valueOf(22.1))
                        .build());
    }

    @Test
    public void testGetPlaceToShipTime_Overall() {
        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("OVERALL");
        assertThat(response.getBody().get(0).getAverageDuration()).isEqualTo(BigDecimal.valueOf(24.5));
    }

    @Test
    public void testGetPlaceToShipTime_ByRegion() {
        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", PlaceToShipGroupBy.REGION.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("RegionID_1");
        assertThat(response.getBody().get(1).getGroupByValue()).isEqualTo("RegionID_2");
    }

    @Test
    public void testGetShipToDeliverTime_Overall() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("OVERALL");
    }

    @Test
    public void testGetShipToDeliverTime_ByRegion() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.REGION.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("RegionID_1");
        assertThat(response.getBody().get(1).getGroupByValue()).isEqualTo("RegionID_2");
    }

    @Test
    public void testGetShipToDeliverTime_ByCourier() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.COURIER))
                .thenReturn(courierTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.COURIER.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("CourierID_1");
        assertThat(response.getBody().get(1).getGroupByValue()).isEqualTo("CourierID_2");
    }

    // Edge case tests

    @Test
    public void testGetPlaceToShipTime_EmptyResponse() {
        // Configure mock service to return empty list
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void testGetShipToDeliverTime_EmptyResponse() {
        // Configure mock service to return empty list
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void testGetPlaceToShipTime_InvalidGroupBy() {
        // Build URL without groupBy param
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
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
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetShipToDeliverTime_InvalidGroupBy() {
        // Build URL without groupBy param
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
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
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetPlaceToShipTime_SameDayRange() {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                sameDate, sameDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
                .queryParam("startDate", sameDate.toString())
                .queryParam("endDate", sameDate.toString())
                .queryParam("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("OVERALL");
    }

    @Test
    public void testGetShipToDeliverTime_SameDayRange() {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                sameDate, sameDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", sameDate.toString())
                .queryParam("endDate", sameDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getGroupByValue()).isEqualTo("OVERALL");
    }

    @Test
    public void testGetPlaceToShipTime_FutureDates() {
        // Test future dates
        LocalDate futureStart = LocalDate.now().plusDays(1);
        LocalDate futureEnd = LocalDate.now().plusDays(30);

        // Configure mock service - should return empty for future dates
        when(mockService.getPlaceToShipTimeResponse(
                futureStart, futureEnd, PlaceToShipGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/place-to-ship-time")
                .queryParam("startDate", futureStart.toString())
                .queryParam("endDate", futureEnd.toString())
                .queryParam("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .toUriString();

        // Execute request
        ResponseEntity<List<FulfillmentTimeResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FulfillmentTimeResponse>>() {});

        // Verify
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void testGetShipToDeliverTime_ServiceException() {
        // Configure mock service to throw exception
        when(mockService.getShipToDeliverTimeResponse(
                any(), any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        // Build URL with query parameters
        String url = UriComponentsBuilder.fromPath("/fulfillment-analytics/ship-to-deliver-time")
                .queryParam("startDate", startDate.toString())
                .queryParam("endDate", endDate.toString())
                .queryParam("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
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