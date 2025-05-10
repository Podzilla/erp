package com.Podzilla.analytics.integration;

import com.Podzilla.analytics.api.dtos.RevenueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.RevenueSummaryRequest;
import com.Podzilla.analytics.api.dtos.RevenueSummaryResponse;
import com.Podzilla.analytics.models.Courier;
import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.models.SalesLineItem;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.repositories.ProductRepository;
import com.Podzilla.analytics.repositories.RegionRepository;
import com.Podzilla.analytics.repositories.SalesLineItemRepository;
import com.Podzilla.analytics.services.RevenueReportService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RevenueReportServiceIntegrationTest {

    @Autowired
    private RevenueReportService revenueReportService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesLineItemRepository salesLineItemRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp() {
        insertTestData();
    }

    private void insertTestData() {
        // Create and save region
        Region region = Region.builder()
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .postalCode("12345")
                .build();
        region = regionRepository.save(region);

        // Create courier
        Courier courier = Courier.builder()
                .name("Test Courier")
                .status(Courier.CourierStatus.ACTIVE)
                .build();
        courier = courierRepository.save(courier);

        // Create customer
        Customer customer = Customer.builder()
                .name("Test Customer")
                .build();
        customer = customerRepository.save(customer);

        // Create products
        Product product1 = Product.builder()
                .name("Phone Case")
                .category("Accessories")
                .build();

        Product product2 = Product.builder()
                .name("Wireless Mouse")
                .category("Electronics")
                .build();

        productRepository.saveAll(List.of(product1, product2));

        // Create order with all required relationships
        Order order1 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 1, 10, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 1, 11, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("100.00"))
                .courier(courier)
                .customer(customer)
                .region(region)
                .build();

        orderRepository.save(order1);

        SalesLineItem item1 = SalesLineItem.builder()
                .order(order1)
                .product(product1)
                .quantity(2)
                .pricePerUnit(new BigDecimal("10.00"))
                .build();

        SalesLineItem item2 = SalesLineItem.builder()
                .order(order1)
                .product(product2)
                .quantity(1)
                .pricePerUnit(new BigDecimal("80.00"))
                .build();

        salesLineItemRepository.saveAll(List.of(item1, item2));
    }

    @Test
    public void getRevenueByCategory_shouldReturnExpectedResults() {
        List<RevenueByCategoryResponse> results = revenueReportService.getRevenueByCategory(
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 3)
        );

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    public void getRevenueSummary_shouldReturnExpectedResults() {
        RevenueSummaryRequest request = RevenueSummaryRequest.builder()
                .startDate(LocalDate.of(2024, 5, 1))
                .endDate(LocalDate.of(2024, 5, 3))
                .period(RevenueSummaryRequest.Period.DAILY)
                .build();

        List<RevenueSummaryResponse> summary = revenueReportService.getRevenueSummary(request);

        assertThat(summary).isNotEmpty();
        assertThat(summary.get(0).getTotalRevenue()).isEqualByComparingTo("100.00");
    }
}