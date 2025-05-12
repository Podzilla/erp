package com.Podzilla.analytics.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentPlaceToShipRequest.PlaceToShipGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentShipToDeliverRequest.ShipToDeliverGroupBy;
import com.Podzilla.analytics.api.dtos.fulfillment.FulfillmentTimeResponse;
import com.Podzilla.analytics.services.FulfillmentAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(FulfillmentReportController.class)
public class FulfillmentReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void testGetPlaceToShipTime_Overall() throws Exception {
        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("OVERALL"))
                .andExpect(jsonPath("$[0].averageDuration").value(24.5));
    }

    @Test
    public void testGetPlaceToShipTime_ByRegion() throws Exception {
        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", PlaceToShipGroupBy.REGION.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("RegionID_1"))
                .andExpect(jsonPath("$[1].groupByValue").value("RegionID_2"));
    }

    @Test
    public void testGetShipToDeliverTime_Overall() throws Exception {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("OVERALL"));
    }

    @Test
    public void testGetShipToDeliverTime_ByRegion() throws Exception {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.REGION))
                .thenReturn(regionTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.REGION.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("RegionID_1"))
                .andExpect(jsonPath("$[1].groupByValue").value("RegionID_2"));
    }

    @Test
    public void testGetShipToDeliverTime_ByCourier() throws Exception {
        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.COURIER))
                .thenReturn(courierTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.COURIER.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("CourierID_1"))
                .andExpect(jsonPath("$[1].groupByValue").value("CourierID_2"));
    }

    // Edge case tests

    @Test
    public void testGetPlaceToShipTime_EmptyResponse() throws Exception {
        // Configure mock service to return empty list
        when(mockService.getPlaceToShipTimeResponse(
                startDate, endDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetShipToDeliverTime_EmptyResponse() throws Exception {
        // Configure mock service to return empty list
        when(mockService.getShipToDeliverTimeResponse(
                startDate, endDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetPlaceToShipTime_InvalidGroupBy() throws Exception {
        // Execute with missing required parameter - should return bad request
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetShipToDeliverTime_InvalidGroupBy() throws Exception {
        // Execute with missing required parameter - should return bad request
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPlaceToShipTime_SameDayRange() throws Exception {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getPlaceToShipTimeResponse(
                sameDate, sameDate, PlaceToShipGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", sameDate.toString())
                .param("endDate", sameDate.toString())
                .param("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("OVERALL"));
    }

    @Test
    public void testGetShipToDeliverTime_SameDayRange() throws Exception {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getShipToDeliverTimeResponse(
                sameDate, sameDate, ShipToDeliverGroupBy.OVERALL))
                .thenReturn(overallTimeResponses);

        // Execute and verify
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", sameDate.toString())
                .param("endDate", sameDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].groupByValue").value("OVERALL"));
    }

    @Test
    public void testGetPlaceToShipTime_FutureDates() throws Exception {
        // Test future dates
        LocalDate futureStart = LocalDate.now().plusDays(1);
        LocalDate futureEnd = LocalDate.now().plusDays(30);

        // Configure mock service - should return empty for future dates
        when(mockService.getPlaceToShipTimeResponse(
                futureStart, futureEnd, PlaceToShipGroupBy.OVERALL))
                .thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/fulfillment/place-to-ship-time")
                .param("startDate", futureStart.toString())
                .param("endDate", futureEnd.toString())
                .param("groupBy", PlaceToShipGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetShipToDeliverTime_ServiceException() throws Exception {
        // Configure mock service to throw exception
        when(mockService.getShipToDeliverTimeResponse(
                any(), any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        // Execute and verify - controller should handle exception with 500 status
        mockMvc.perform(get("/fulfillment/ship-to-deliver-time")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .param("groupBy", ShipToDeliverGroupBy.OVERALL.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}