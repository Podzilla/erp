package com.Podzilla.analytics.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

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

import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.repositories.RegionRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CustomerReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private EntityManager entityManager;

    private static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private Customer customer1;
    private Customer customer2;
    private Region region1;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        regionRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Create test region
        region1 = regionRepository.save(Region.builder()
                .city("Sample City")
                .state("Sample State")
                .country("Sample Country")
                .postalCode("12345")
                .build());

        // Create test customers
        customer1 = customerRepository.save(Customer.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .build());

        customer2 = customerRepository.save(Customer.builder()
                .id(UUID.randomUUID())
                .name("Jane Smith")
                .build());

        // Create test orders
        order1 = orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("1000.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(2))
                .status(Order.OrderStatus.DELIVERED)
                .customer(customer1)
                .region(region1)
                .build());

        order2 = orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("500.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(1))
                .status(Order.OrderStatus.DELIVERED)
                .customer(customer1)
                .region(region1)
                .build());

        order3 = orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("2000.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(3))
                .status(Order.OrderStatus.DELIVERED)
                .customer(customer2)
                .region(region1)
                .build());

        entityManager.flush();
        entityManager.clear();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        regionRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getTopSpenders_ShouldReturnListOfTopSpenders() throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now();

        mockMvc.perform(get("/customer-analytics/top-spenders")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE))
                .param("page", "0")
                .param("size", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerName").value(customer2.getName()))
                .andExpect(jsonPath("$[0].totalSpending").value(closeTo(order3.getTotalAmount().doubleValue(), 0.01)))
                .andExpect(jsonPath("$[1].customerName").value(customer1.getName()))
                .andExpect(jsonPath("$[1].totalSpending")
                        .value(closeTo(order1.getTotalAmount().add(order2.getTotalAmount()).doubleValue(), 0.01)));
    }

    @Test
    void getTopSpenders_ShouldReturnEmptyListWhenNoOrdersInDateRange() throws Exception {
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(2);

        mockMvc.perform(get("/customer-analytics/top-spenders")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE))
                .param("page", "0")
                .param("size", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getTopSpenders_ShouldHandlePagination() throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now();

        mockMvc.perform(get("/customer-analytics/top-spenders")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE))
                .param("page", "0")
                .param("size", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerName").value(customer2.getName()))
                .andExpect(jsonPath("$[0].totalSpending").value(closeTo(order3.getTotalAmount().doubleValue(), 0.01)));
    }

    @Test
    void getTopSpenders_ShouldExcludeFailedOrders() throws Exception {
        Order failedOrder = orderRepository.save(Order.builder()
                .id(UUID.randomUUID())
                .totalAmount(new BigDecimal("3000.00"))
                .finalStatusTimestamp(LocalDateTime.now().minusDays(1))
                .status(Order.OrderStatus.DELIVERY_FAILED)
                .customer(customer1)
                .region(region1)
                .build());

        entityManager.flush();
        entityManager.clear();

        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now();

        mockMvc.perform(get("/customer-analytics/top-spenders")
                .param("startDate", startDate.format(ISO_LOCAL_DATE))
                .param("endDate", endDate.format(ISO_LOCAL_DATE))
                .param("page", "0")
                .param("size", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerName").value(customer2.getName()))
                .andExpect(jsonPath("$[0].totalSpending").value(closeTo(order3.getTotalAmount().doubleValue(), 0.01)))
                .andExpect(jsonPath("$[1].customerName").value(customer1.getName()))
                .andExpect(jsonPath("$[1].totalSpending")
                        .value(closeTo(order1.getTotalAmount().add(order2.getTotalAmount()).doubleValue(), 0.01)))
                .andExpect(jsonPath("$[1].totalSpending").value(not(closeTo(order1.getTotalAmount()
                        .add(order2.getTotalAmount()).add(failedOrder.getTotalAmount()).doubleValue(), 0.01))));
    }
}