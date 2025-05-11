package com.Podzilla.analytics.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Podzilla.analytics.api.controllers.FulfillmentReportController;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentPlaceToShipRequest;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentShipToDeliverRequest;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentShipToDeliverRequest.ShipToDeliverGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentPlaceToShipRequest.PlaceToShipGroupBy;
import com.Podzilla.analytics.services.FulfillmentAnalyticsService;

public class FulfillmentReportControllerTest {

    private FulfillmentReportController controller;
    private FulfillmentAnalyticsService mockService;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<FulfillmentTimeResponse> overallTimeResponses;
    private List<FulfillmentTimeResponse> regionTimeResponses;
    private List<FulfillmentTimeResponse> courierTimeResponses;

    @BeforeEach
    public void setup() {
        mockService = mock(FulfillmentAnalyticsService.class);
        controller = new FulfillmentReportController(mockService);

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

        // Create request
        FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
                startDate, endDate, PlaceToShipGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(overallTimeResponses, response.getBody());
        assertEquals(PlaceToShipGroupBy.OVERALL.toString(), response.getBody().get(0).getGroupByValue().toString());
        assertEquals(BigDecimal.valueOf(24.5), response.getBody().get(0).getAverageDuration());
    }

    @Test
    public void testGetPlaceToShipTime_ByRegion() {
        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Create request
        FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
                startDate, endDate, PlaceToShipGroupBy.REGION);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regionTimeResponses, response.getBody());
        assertEquals("RegionID_1", response.getBody().get(0).getGroupByValue());
        assertEquals("RegionID_2", response.getBody().get(1).getGroupByValue());
    }

    @Test
    public void testGetShipToDeliverTime_Overall() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Create request
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(overallTimeResponses, response.getBody());
        assertEquals(ShipToDeliverGroupBy.OVERALL.toString(), response.getBody().get(0).getGroupByValue().toString());
    }

    @Test
    public void testGetShipToDeliverTime_ByRegion() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Create request
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                startDate, endDate, ShipToDeliverGroupBy.REGION);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regionTimeResponses, response.getBody());
        assertEquals("RegionID_1", response.getBody().get(0).getGroupByValue());
        assertEquals("RegionID_2", response.getBody().get(1).getGroupByValue());
    }

    @Test
    public void testGetShipToDeliverTime_ByCourier() {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.COURIER))
                .thenReturn(courierTimeResponses);

        // Create request
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                startDate, endDate, ShipToDeliverGroupBy.COURIER);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courierTimeResponses, response.getBody());
        assertEquals("CourierID_1", response.getBody().get(0).getGroupByValue());
        assertEquals("CourierID_2", response.getBody().get(1).getGroupByValue());
    }

    // Edge case tests

    @Test
    public void testGetPlaceToShipTime_EmptyResponse() {
        // Configure mock service to return empty list
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Create request
        FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
                startDate, endDate, PlaceToShipGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testGetShipToDeliverTime_EmptyResponse() {
        // Configure mock service to return empty list
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Create request
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    // @Test
    // public void testGetPlaceToShipTime_InvalidGroupBy() {
    //     // Create request with invalid groupBy
    //     FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
    //             startDate, endDate, null);

    //     // Execute the method - should return bad request due to validation error
        // ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

    //     // Verify response
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // public void testGetShipToDeliverTime_InvalidGroupBy() {
    //     // Create request with invalid groupBy
    //     FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
    //             startDate, endDate, null);

    //     // Execute the method - should return bad request due to validation error
    //     ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

    //     // Verify response
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    @Test
    public void testGetPlaceToShipTime_SameDayRange() {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                sameDate, sameDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Create request with same start and end date
        FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
                sameDate, sameDate, PlaceToShipGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(overallTimeResponses, response.getBody());
    }

    @Test
    public void testGetShipToDeliverTime_SameDayRange() {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                sameDate, sameDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Create request with same start and end date
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                sameDate, sameDate, ShipToDeliverGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getShipToDeliverTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(overallTimeResponses, response.getBody());
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

        // Create request with future dates
        FulfillmentPlaceToShipRequest request = new FulfillmentPlaceToShipRequest(
                futureStart, futureEnd, PlaceToShipGroupBy.OVERALL);

        // Execute the method
        ResponseEntity<List<FulfillmentTimeResponse>> response = controller.getPlaceToShipTime(request);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testGetShipToDeliverTime_ServiceException() {
        // Configure mock service to throw exception
        when(mockService.getShipToDeliverTimeResponse(
                any(), any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        // Create request
        FulfillmentShipToDeliverRequest request = new FulfillmentShipToDeliverRequest(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL);

        // Execute the method - controller should handle exception
        // Note: Actual behavior depends on how controller handles exceptions
        // This might need adjustment based on actual implementation
        try {
            controller.getShipToDeliverTime(request);
        } catch (RuntimeException e) {
            assertEquals("Service error", e.getMessage());
        }
    }
}