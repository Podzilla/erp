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

import com.Podzilla.analytics.api.dtos.profit.ProfitByCategory;
import com.Podzilla.analytics.services.ProfitAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(ProfitReportController.class)
public class ProfitReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void testGetProfitByCategory_Success() throws Exception {
        // Configure mock service
        when(mockService.getProfitByCategory(startDate, endDate))
                .thenReturn(profitData);

        // Execute and verify
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("Electronics"))
                .andExpect(jsonPath("$[0].totalRevenue").value(10000.50))
                .andExpect(jsonPath("$[0].grossProfit").value(2500.25))
                .andExpect(jsonPath("$[1].category").value("Clothing"))
                .andExpect(jsonPath("$[1].grossProfitMargin").value(45.45));
    }

    @Test
    public void testGetProfitByCategory_EmptyResult() throws Exception {
        // Configure mock service to return empty list
        when(mockService.getProfitByCategory(startDate, endDate))
                .thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetProfitByCategory_MissingStartDate() throws Exception {
        // Execute with missing required parameter - should return bad request
        mockMvc.perform(get("/profit/by-category")
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProfitByCategory_MissingEndDate() throws Exception {
        // Execute with missing required parameter - should return bad request
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", startDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProfitByCategory_InvalidDateFormat() throws Exception {
        // Execute with invalid date format - should return bad request
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", "2024-01-01")
                .param("endDate", "invalid-date")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProfitByCategory_StartDateAfterEndDate() throws Exception {
        // Set up dates where start is after end
        LocalDate invalidStart = LocalDate.of(2024, 2, 1);
        LocalDate invalidEnd = LocalDate.of(2024, 1, 1);

        // Execute with invalid date range - depends on how controller/validation handles this
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", invalidStart.toString())
                .param("endDate", invalidEnd.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetProfitByCategory_FutureDateRange() throws Exception {
        // Set up future dates
        LocalDate futureStart = LocalDate.now().plusDays(1);
        LocalDate futureEnd = LocalDate.now().plusDays(30);

        // Configure mock service - should return empty data for future dates
        when(mockService.getProfitByCategory(futureStart, futureEnd))
                .thenReturn(Collections.emptyList());

        // Execute and verify
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", futureStart.toString())
                .param("endDate", futureEnd.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetProfitByCategory_SameDayRange() throws Exception {
        // Test same start and end date
        LocalDate sameDate = LocalDate.of(2024, 1, 1);

        // Configure mock service
        when(mockService.getProfitByCategory(sameDate, sameDate))
                .thenReturn(profitData);

        // Execute and verify
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", sameDate.toString())
                .param("endDate", sameDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("Electronics"));
    }

    @Test
    public void testGetProfitByCategory_ServiceException() throws Exception {
        // Configure mock service to throw exception
        when(mockService.getProfitByCategory(any(), any()))
                .thenThrow(new RuntimeException("Service error"));

        // Execute and verify - controller should handle exception with 500 status
        mockMvc.perform(get("/profit/by-category")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
} 