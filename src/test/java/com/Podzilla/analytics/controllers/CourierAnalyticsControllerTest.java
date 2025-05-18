package com.Podzilla.analytics.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.Podzilla.analytics.models.Courier;
import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.repositories.RegionRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class CourierAnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    private static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private Customer customer1;
    private Region region1;
    private Courier courierJane;
    private Courier courierJohn;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        courierRepository.deleteAll();
        customerRepository.deleteAll();
        regionRepository.deleteAll();

        entityManager.flush();
        entityManager.clear();

        customer1 = customerRepository.save(Customer.builder()
                .id(UUID.randomUUID())
                .name("John Doe").build());
        region1 = regionRepository.save(Region.builder()
                .id(UUID.randomUUID())
                .city("Sample City")
                .state("Sample State")
                .country("Sample Country")
                .postalCode("12345")
                .build());

        courierJane = courierRepository.save(Courier.builder()
                .id(UUID.randomUUID())
                .name("Jane Smith")
                .status(Courier.CourierStatus.ACTIVE)
                .build());

        courierJohn = courierRepository.save(Courier.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .status(Courier.CourierStatus.ACTIVE)
                .build());

        orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("50.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(3))
                .status(Order.OrderStatus.COMPLETED)
                .numberOfItems(1)
                .courierRating(new BigDecimal("4.0"))
                .customer(customer1)
                .courier(courierJane)
                .region(region1)
                .build());

        orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("75.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(3))
                .status(Order.OrderStatus.COMPLETED)
                .numberOfItems(1)
                .courierRating(new BigDecimal("4.0"))
                .customer(customer1)
                .courier(courierJane)
                .region(region1)
                .build());

        orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("120.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(1))
                .status(Order.OrderStatus.COMPLETED)
                .numberOfItems(2)
                .courierRating(new BigDecimal("5.0"))
                .customer(customer1)
                .courier(courierJane)
                .region(region1)
                .build());

        orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("30.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(2))
                .status(Order.OrderStatus.FAILED)
                .numberOfItems(1)
                .courierRating(null)
                .customer(customer1)
                .courier(courierJohn)
                .region(region1)
                .build());

        orderRepository.save(Order.builder()    
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("90.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(2))
                .status(Order.OrderStatus.COMPLETED)
                .numberOfItems(1)
                .courierRating(new BigDecimal("3.0"))
                .customer(customer1)
                .courier(courierJohn)
                .region(region1)
                .build());

        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        courierRepository.deleteAll();
        customerRepository.deleteAll();
        regionRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getCourierDeliveryCounts_shouldReturnCountsForSpecificDateRange() throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now().minusDays(2);

        mockMvc.perform(get("/courier-analytics/delivery-counts")
                .param("startDate", startDate.format(
                        ISO_LOCAL_DATE))
                .param("endDate", endDate.format(
                        ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].deliveryCount").value(hasItem(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].deliveryCount").value(hasItem(2)));
    }

    @Test
    void getCourierDeliveryCounts_shouldReturnZeroCountsWhenNoDeliveriesInDateRange()
            throws Exception {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(2);

        mockMvc.perform(get("/courier-analytics/delivery-counts")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].courierName").value(hasItem("Jane Smith")))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].deliveryCount").value(hasItem(0)))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].courierName").value(hasItem("John Doe")))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].deliveryCount").value(hasItem(0)));
    }

    @Test
    void getCourierDeliveryCounts_shouldHandleInvalidDateRange_startDateAfterEndDate()
            throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().minusDays(3);

        mockMvc.perform(get("/courier-analytics/delivery-counts")
                .param("startDate", startDate.format(
                        ISO_LOCAL_DATE))
                .param("endDate", endDate.format(
                        ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCourierSuccessRate_shouldReturnSuccessRatesForSpecificDateRange()
            throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(4);
        LocalDateTime endDate = LocalDateTime.now().minusDays(2);

        mockMvc.perform(get("/courier-analytics/success-rate")
                .param("startDate", startDate.format(
                        ISO_LOCAL_DATE))
                .param("endDate", endDate.format(
                        ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(
                        jsonPath("$[?(@.courierName == 'Jane Smith')].successRate").value(hasItem(closeTo(1.0, 0.001))))
                .andExpect(
                        jsonPath("$[?(@.courierName == 'John Doe')].successRate").value(hasItem(closeTo(0.5, 0.001))));
    }

    @Test
    void getCourierAverageRating_shouldReturnAllAverageRatingsWhenNoDateRangeProvided()
            throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(4);
        LocalDateTime endDate = LocalDateTime.now();
        mockMvc.perform(get("/courier-analytics/average-rating")
                .param("startDate", startDate.format(
                        ISO_LOCAL_DATE))
                .param("endDate", endDate.format(
                        ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].averageRating")
                        .value(hasItem(closeTo(4.333, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].averageRating")
                        .value(hasItem(closeTo(3.0, 0.001))));
    }

    @Test
    void getCourierAverageRating_shouldReturnAverageRatingsForSpecificDateRange()
            throws Exception {

        LocalDateTime startDate = LocalDateTime.now().minusDays(4);
        LocalDateTime endDate = LocalDateTime.now().minusDays(2);

        mockMvc.perform(get("/courier-analytics/average-rating")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].averageRating")
                        .value(hasItem(closeTo(4.0, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].averageRating")
                        .value(hasItem(closeTo(3.0, 0.001))));
    }

    @Test
    void getCourierPerformanceReport_shouldReturnAllPerformanceReportsWhenNoDateRangeProvided()
            throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(4);
        LocalDateTime endDate = LocalDateTime.now();
        mockMvc.perform(get("/courier-analytics/performance-report")
                .param("startDate", startDate.format(
                        ISO_LOCAL_DATE))
                .param("endDate", endDate.format(
                        ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].deliveryCount").value(hasItem(3)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].successRate")
                        .value(hasItem(closeTo(1.0, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].averageRating")
                        .value(hasItem(closeTo(4.333, 0.001))))

                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].deliveryCount").value(hasItem(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].successRate")
                        .value(hasItem(closeTo(0.5, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].averageRating")
                        .value(hasItem(closeTo(3.0, 0.001))));
    }

    @Test
    void getCourierPerformanceReport_shouldReturnPerformanceReportsForSpecificDateRange()
            throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(4);
        LocalDateTime endDate = LocalDateTime.now().minusDays(2);

        mockMvc.perform(get("/courier-analytics/performance-report")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].deliveryCount").value(hasItem(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].successRate")
                        .value(hasItem(closeTo(1.0, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'Jane Smith')].averageRating")
                        .value(hasItem(closeTo(4.0, 0.001))))

                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].deliveryCount").value(hasItem(2)))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].successRate")
                        .value(hasItem(closeTo(0.5, 0.001))))
                .andExpect(jsonPath("$[?(@.courierName == 'John Doe')].averageRating")
                        .value(hasItem(closeTo(3.0, 0.001))));
    }
}
