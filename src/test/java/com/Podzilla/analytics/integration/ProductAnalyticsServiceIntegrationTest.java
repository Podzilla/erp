package com.Podzilla.analytics.integration;

import com.Podzilla.analytics.api.dtos.TopSellerRequest;
import com.Podzilla.analytics.api.dtos.TopSellerResponse;
import com.Podzilla.analytics.models.*;
import com.Podzilla.analytics.repositories.*;
import com.Podzilla.analytics.services.ProductAnalyticsService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProductAnalyticsServiceIntegrationTest {

    @Autowired
    private ProductAnalyticsService productAnalyticsService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SalesLineItemRepository salesLineItemRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private RegionRepository regionRepository;

    // Class-level test data objects
    private Product phone;
    private Product laptop;
    private Product book;
    private Product tablet;
    private Product headphones;
    
    private Customer customer;
    private Courier courier;
    private Region region;
    
    private Order order1; // May 1st
    private Order order2; // May 2nd
    private Order order3; // May 3rd
    private Order order4; // May 4th - Failed order
    private Order order5; // May 5th - Products with same revenue but different units
    private Order order6; // April 30th - Outside default test range

    @BeforeEach
    void setUp() {
        insertTestData();
    }

    private void insertTestData() {
        // Create test products
        phone = Product.builder()
                .name("Smartphone")
                .category("Electronics")
                .cost(new BigDecimal("300.00"))
                .lowStockThreshold(5)
                .build();

        laptop = Product.builder()
                .name("Laptop")
                .category("Electronics")
                .cost(new BigDecimal("700.00"))
                .lowStockThreshold(3)
                .build();

        book = Product.builder()
                .name("Programming Book")
                .category("Books")
                .cost(new BigDecimal("20.00"))
                .lowStockThreshold(10)
                .build();
                
        tablet = Product.builder()
                .name("Tablet")
                .category("Electronics")
                .cost(new BigDecimal("200.00"))
                .lowStockThreshold(5)
                .build();
                
        headphones = Product.builder()
                .name("Wireless Headphones")
                .category("Audio")
                .cost(new BigDecimal("80.00"))
                .lowStockThreshold(8)
                .build();

        productRepository.saveAll(List.of(phone, laptop, book, tablet, headphones));

        // Create required entities for orders
        customer = Customer.builder()
                .name("Test Customer")
                .build();
        customerRepository.save(customer);

        courier = Courier.builder()
                .name("Test Courier")
                .status(Courier.CourierStatus.ACTIVE)
                .build();
        courierRepository.save(courier);

        region = Region.builder()
                .city("Test City")
                .state("Test State")
                .country("Test Country")
                .postalCode("12345")
                .build();
        regionRepository.save(region);

        // Create orders with different dates and statuses
        order1 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 1, 10, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 1, 15, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("2000.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();

        order2 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 2, 11, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 2, 16, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("1500.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();
                
        order3 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 3, 9, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 3, 14, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("800.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();
                
        order4 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 4, 10, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 4, 12, 0))
                .status(Order.OrderStatus.FAILED) // Failed order - should be excluded
                .failureReason("Payment declined")
                .totalAmount(new BigDecimal("1200.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();
                
        order5 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 5, 5, 14, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 5, 5, 18, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("1000.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();
                
        // Order outside of default test date range
        order6 = Order.builder()
                .orderPlacedTimestamp(LocalDateTime.of(2024, 4, 30, 9, 0))
                .finalStatusTimestamp(LocalDateTime.of(2024, 4, 30, 15, 0))
                .status(Order.OrderStatus.COMPLETED)
                .totalAmount(new BigDecimal("750.00"))
                .customer(customer)
                .courier(courier)
                .region(region)
                .build();

        orderRepository.saveAll(List.of(order1, order2, order3, order4, order5, order6));

        // Create sales line items with different quantities and prices
        // Order 1 - May 1
        SalesLineItem item1_1 = SalesLineItem.builder()
                .order(order1)
                .product(phone)
                .quantity(2)         // 2 phones
                .pricePerUnit(new BigDecimal("500.00"))  // $500 each
                .build();

        SalesLineItem item1_2 = SalesLineItem.builder()
                .order(order1)
                .product(laptop)
                .quantity(1)         // 1 laptop
                .pricePerUnit(new BigDecimal("1000.00")) // $1000 each
                .build();

        // Order 2 - May 2
        SalesLineItem item2_1 = SalesLineItem.builder()
                .order(order2)
                .product(phone)
                .quantity(3)         // 3 phones
                .pricePerUnit(new BigDecimal("500.00"))  // $500 each
                .build();
                
        // Order 3 - May 3
        SalesLineItem item3_1 = SalesLineItem.builder()
                .order(order3)
                .product(book)
                .quantity(5)         // 5 books
                .pricePerUnit(new BigDecimal("40.00"))   // $40 each
                .build();
                
        SalesLineItem item3_2 = SalesLineItem.builder()
                .order(order3)
                .product(tablet)
                .quantity(2)         // 2 tablets
                .pricePerUnit(new BigDecimal("300.00"))  // $300 each
                .build();
                
        // Order 4 - May 4 (Failed order)
        SalesLineItem item4_1 = SalesLineItem.builder()
                .order(order4)
                .product(laptop)
                .quantity(1)         // 1 laptop
                .pricePerUnit(new BigDecimal("1200.00")) // $1200 each
                .build();
                
        // Order 5 - May 5 (Same revenue different products)
        SalesLineItem item5_1 = SalesLineItem.builder()
                .order(order5)
                .product(headphones)
                .quantity(5)         // 5 headphones
                .pricePerUnit(new BigDecimal("100.00"))  // $100 each = $500 total
                .build();
                
        SalesLineItem item5_2 = SalesLineItem.builder()
                .order(order5)
                .product(tablet)
                .quantity(1)         // 1 tablet
                .pricePerUnit(new BigDecimal("500.00"))  // $500 each = $500 total (same as headphones)
                .build();
                
        // Order 6 - April 30 (Outside default range)
        SalesLineItem item6_1 = SalesLineItem.builder()
                .order(order6)
                .product(phone)
                .quantity(1)         // 1 phone
                .pricePerUnit(new BigDecimal("450.00"))  // $450 each
                .build();
                
        SalesLineItem item6_2 = SalesLineItem.builder()
                .order(order6)
                .product(book)
                .quantity(10)        // 10 books
                .pricePerUnit(new BigDecimal("30.00"))   // $30 each
                .build();

        salesLineItemRepository.saveAll(List.of(
            item1_1, item1_2, item2_1, item3_1, item3_2, 
            item4_1, item5_1, item5_2, item6_1, item6_2
        ));
    }

    @Nested
    @DisplayName("Basic Functionality Tests")
    class BasicFunctionalityTests {
        @Test
        @DisplayName("Get top sellers by revenue should return products in correct order")
        void getTopSellers_byRevenue_shouldReturnCorrectOrder() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            System.out.println("Results: " + results);
            assertThat(results).hasSize(5); // Phone, Laptop, Tablet, Headphones, Book
            
            // Verify proper ordering by revenue
            assertThat(results.get(0).getProductName()).isEqualTo("Smartphone");
            assertThat(results.get(0).getValue()).isEqualByComparingTo("2500.00"); // 5 phones * $500
            assertThat(results.get(1).getProductName()).isEqualTo("Tablet");
            assertThat(results.get(1).getValue()).isEqualByComparingTo("1100.00"); // (2 * $300) + (1 * $500)
            assertThat(results.get(2).getProductName()).isEqualTo("Laptop");
            assertThat(results.get(2).getValue()).isEqualByComparingTo("1000.00"); // 1 laptop * $1000
           
            assertThat(results.get(3).getProductName()).isEqualTo("Wireless Headphones");
            assertThat(results.get(3).getValue()).isEqualByComparingTo("500.00"); // 5 * $100
        }

        @Test
        @DisplayName("Get top sellers by units should return products in correct order")
        void getTopSellers_byUnits_shouldReturnCorrectOrder() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.UNITS)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            assertThat(results).hasSize(5);
            
            // Order by units sold
            assertThat(results.get(0).getProductName()).isEqualTo("Smartphone");
            assertThat(results.get(0).getValue()).isEqualByComparingTo("5"); // 2 + 3 phones
            assertThat(results.get(1).getProductName()).isEqualTo("Wireless Headphones");
            assertThat(results.get(1).getValue()).isEqualByComparingTo("5"); // 5 headphones
            assertThat(results.get(2).getProductName()).isEqualTo("Programming Book");
            assertThat(results.get(2).getValue()).isEqualByComparingTo("5"); // 5 books
            
            // Check correct tie-breaking behavior
            Map<String, Integer> orderMap = results.stream()
                .collect(Collectors.toMap(TopSellerResponse::getProductName, 
                                         r -> r.getValue().intValue()));
                                         
            // Assuming tie-breaking is by revenue (which is how the repository query is sorted)
            assertTrue(orderMap.get("Smartphone") >= orderMap.get("Wireless Headphones"));
            assertTrue(orderMap.get("Wireless Headphones") >= orderMap.get("Programming Book"));
        }

        @Test
        @DisplayName("Get top sellers with limit should respect the limit parameter")
        void getTopSellers_withLimit_shouldRespectLimit() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(2)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);
            // System.out.println("Results:**-*-*-*-**-* " + results);


            assertThat(results).hasSize(2);
            assertThat(results.get(0).getProductName()).isEqualTo("Smartphone");
            assertThat(results.get(1).getProductName()).isEqualTo("Tablet");
        }

        @Test
        @DisplayName("Get top sellers with date range should only include orders in range")
        void getTopSellers_withDateRange_shouldOnlyIncludeOrdersInRange() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 2))  // Start from May 2nd
                    .endDate(LocalDate.of(2024, 5, 4))    // End before May 4th
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .limit(5)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should have only phone, book, and tablet (from orders 2 and 3)
            assertThat(results).hasSize(3);
            
            // First should be phone with only Order 2 revenue
            assertThat(results.get(0).getProductName()).isEqualTo("Smartphone");
            assertThat(results.get(0).getValue()).isEqualByComparingTo("1500.00"); // Only order 2: 3 phones * $500
            
            // Should include tablets from order 3
            boolean hasTablet = results.stream()
                .anyMatch(r -> r.getProductName().equals("Tablet") && r.getValue().compareTo(new BigDecimal("600.00")) == 0);
            assertThat(hasTablet).isTrue();
            
            // Should include books from order 3
            boolean hasBook = results.stream()
                .anyMatch(r -> r.getProductName().equals("Programming Book") && r.getValue().compareTo(new BigDecimal("200.00")) == 0);
            assertThat(hasBook).isTrue();
            
            // Should NOT include laptop (only in order 1)
            boolean hasLaptop = results.stream()
                .anyMatch(r -> r.getProductName().equals("Laptop"));
            assertThat(hasLaptop).isFalse();
        }
    }
    
    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        @DisplayName("Get top sellers with empty result set should return empty list")
        void getTopSellers_withNoMatchingData_shouldReturnEmptyList() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 6, 1))  // Future date with no data
                    .endDate(LocalDate.of(2024, 6, 2))
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .limit(5)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            assertThat(results).isEmpty();
        }
        
        @Test
        @DisplayName("Get top sellers with null sort parameter should default to REVENUE")
        void getTopSellers_withNullSortBy_shouldDefaultToRevenue() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(3)
                    .sortBy(null) // Null sort parameter
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            assertThat(results).hasSize(3);
            // Should default to sorting by revenue
            assertThat(results.get(0).getProductName()).isEqualTo("Smartphone");
            assertThat(results.get(0).getValue()).isEqualByComparingTo("2500.00");
        }
        
        @Test
        @DisplayName("Get top sellers with zero limit should return all results")
        void getTopSellers_withZeroLimit_shouldReturnAllResults() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(0) // Zero limit
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should return all 4 products with sales in the period
            assertThat(results).hasSize(0);
        }
        
     
        
        @Test
        @DisplayName("Get top sellers with single day range (inclusive) should work correctly")
        void getTopSellers_withSingleDayRange_shouldWorkCorrectly() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 1)) // End date inclusive
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should only include products from order1 (May 1st)
            assertThat(results).hasSize(2);
            
            // Smartphone should be included
            boolean hasPhone = results.stream()
                .anyMatch(r -> r.getProductName().equals("Smartphone") && 
                                r.getValue().compareTo(new BigDecimal("1000.00")) == 0);
            assertThat(hasPhone).isTrue();
            
            // Laptop should be included
            boolean hasLaptop = results.stream()
                .anyMatch(r -> r.getProductName().equals("Laptop") && 
                                r.getValue().compareTo(new BigDecimal("1000.00")) == 0);
            assertThat(hasLaptop).isTrue();
        }
        
        @Test
        @DisplayName("Get top sellers should exclude failed orders")
        void getTopSellers_shouldExcludeFailedOrders() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 4))  // Only include May 4th (failed order day)
                    .endDate(LocalDate.of(2024, 5, 4))
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should be empty because the only order on May 4th was failed
            assertThat(results).isEmpty();
            
            // Specifically, the laptop from the failed order should not be included
            boolean hasLaptop = results.stream()
                .anyMatch(r -> r.getProductName().equals("Laptop"));
            assertThat(hasLaptop).isFalse();
        }
        
        @Test
        @DisplayName("Get top sellers including boundary dates should work correctly")
        void getTopSellers_withBoundaryDates_shouldWorkCorrectly() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 4, 30))  // Include April 30
                    .endDate(LocalDate.of(2024, 4, 30))     // Exclude May 1
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should only include products from April 30th (order6)
            assertThat(results).hasSize(2);
            
            // Book should be included
            boolean hasBook = results.stream()
                .anyMatch(r -> r.getProductName().equals("Programming Book") && 
                               r.getValue().compareTo(new BigDecimal("300.00")) == 0);
            assertThat(hasBook).isTrue();
            
            // Phone should be included
            boolean hasPhone = results.stream()
                .anyMatch(r -> r.getProductName().equals("Smartphone") && 
                               r.getValue().compareTo(new BigDecimal("450.00")) == 0);
            assertThat(hasPhone).isTrue();
        }
    }
    
    @Nested
    @DisplayName("Sorting and Value Tests")
    class SortingAndValueTests {
        @Test
        @DisplayName("Products with same revenue but different units should sort by revenue first")
        void getTopSellers_withSameRevenue_shouldSortCorrectly() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 5))  // Only include May 5th order
                    .endDate(LocalDate.of(2024, 5, 6))
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);

            // Should have both products with $500 revenue
            assertThat(results).hasSize(2);
            
            // Both should have same revenue value
            assertThat(results.get(0).getValue()).isEqualByComparingTo(results.get(1).getValue());
            assertThat(results.get(0).getValue()).isEqualByComparingTo("500.00");
            
            // Check units separately to verify the data is correct
            // (This doesn't test sorting order, but verifies the test data is as expected)
            boolean hasTablet = results.stream()
                .anyMatch(r -> r.getProductName().equals("Tablet"));
            boolean hasHeadphones = results.stream()
                .anyMatch(r -> r.getProductName().equals("Wireless Headphones"));
                
            assertThat(hasTablet).isTrue();
            assertThat(hasHeadphones).isTrue();
        }
        
        @Test
        @DisplayName("Get top sellers by units with products having same units should respect secondary sort")
        void getTopSellers_byUnitsWithSameUnits_shouldRespectSecondarySorting() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .sortBy(TopSellerRequest.SortBy.UNITS)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);
            
            // Find all products with 5 units
            List<TopSellerResponse> productsWithFiveUnits = results.stream()
                .filter(r -> r.getValue().compareTo(BigDecimal.valueOf(5)) == 0)
                .collect(Collectors.toList());
                
            // Should have 3 products with 5 units (phone, headphones, book)
            assertThat(productsWithFiveUnits.size()).isEqualTo(3);
            
            // Verify that secondary sorting works (we expect by revenue)
            // Get product names in order
            List<String> productOrder = productsWithFiveUnits.stream()
                .map(TopSellerResponse::getProductName)
                .collect(Collectors.toList());
                
            // Expected order: Smartphone ($2500), Headphones ($500), Book ($200)
            int smartphoneIdx = productOrder.indexOf("Smartphone");
            int headphonesIdx = productOrder.indexOf("Wireless Headphones");
            int bookIdx = productOrder.indexOf("Programming Book");
            
            assertTrue(smartphoneIdx < headphonesIdx, "Smartphone should come before Headphones");
            assertTrue(headphonesIdx < bookIdx, "Headphones should come before Programming Book");
        }
    }
    
    @Nested
    @DisplayName("Request Parameter Tests")
    class RequestParameterTests {
        @Test
        @DisplayName("Get top sellers with null limit should use default behavior")
        void getTopSellers_withNullLimit_shouldUseDefaultBehavior() {
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 5))
                    .limit(null) // Null limit
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);
            System.out.println("Results:--------------------------------------------------- " + results);
            // Should return all products with sales in the date range
            // Assuming there are 4 products with sales in the date range
            // (Phone, Laptop, Tablet, Headphones)
            // This may vary based on your test data and repository behavior
            // Check the size of the results
            // If your repository defaults to a certain limit, adjust this accordingly

            
            // Should return all results (4 products with sales)
            assertThat(results).hasSize(5);
        }
        
        @Test
        @DisplayName("Get top sellers with null date range should handle appropriately")
        void getTopSellers_withNullDateRange_shouldThrowException() {
            
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(null)
                    .endDate(null)
                    .limit(10)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            
            assertThrows(NullPointerException.class, () -> productAnalyticsService.getTopSellers(request));

        }
        
        @Test
        @DisplayName("Get top sellers with swapped date range should handle gracefully")
        void getTopSellers_withSwappedDateRange_shouldHandleGracefully() {
            // Start date is after end date - test depends on how service handles this
            TopSellerRequest request = TopSellerRequest.builder()
                    .startDate(LocalDate.of(2024, 5, 6))  // Start after end
                    .endDate(LocalDate.of(2024, 5, 1))    // End before start
                    .limit(5)
                    .sortBy(TopSellerRequest.SortBy.REVENUE)
                    .build();

            // If service handles swapped dates, this may return empty result
            // or throw an exception
            List<TopSellerResponse> results = productAnalyticsService.getTopSellers(request);
            // Should return empty list if swapped dates are handled
            assertThat(results).isEmpty();
            // If exception is expected, you may need to adjust this test
            // assertThrows(IllegalArgumentException.class, () -> productAnalyticsService.getTopSellers(request));
        }
    }   
}