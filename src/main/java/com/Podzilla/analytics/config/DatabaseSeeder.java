package com.Podzilla.analytics.config;

import com.Podzilla.analytics.models.*;
import com.Podzilla.analytics.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RegionRepository regionRepository;
    private final OrderRepository orderRepository;
    // private final SalesLineItemRepository salesLineItemRepository;
    private final InventorySnapshotRepository inventorySnapshotRepository;

    private final Random random = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Checking if database needs seeding...");

        if (courierRepository.count() > 0) {
            System.out.println("Database already seeded. Skipping.");
            return;
        }

        System.out.println("Seeding database...");

        // --- Seed Independent Entities First ---

        // Regions
        Region region1 = regionRepository
                .save(Region.builder().city("Metropolis").state("NY").country("USA").postalCode("10001").build());
        Region region2 = regionRepository
                .save(Region.builder().city("Gotham").state("NJ").country("USA").postalCode("07001").build());
        Region region3 = regionRepository
                .save(Region.builder().city("Star City").state("CA").country("USA").postalCode("90210").build());
        List<Region> regions = Arrays.asList(region1, region2, region3);
        System.out.println("Seeded Regions: " + regions.size());

        // Products
        Product prod1 = productRepository.save(Product.builder().name("Podzilla Pro").category("Electronics")
                .cost(new BigDecimal("199.99")).lowStockThreshold(10).build());
        Product prod2 = productRepository.save(Product.builder().name("Podzilla Mini").category("Electronics")
                .cost(new BigDecimal("99.99")).lowStockThreshold(20).build());
        Product prod3 = productRepository.save(Product.builder().name("Charging Case").category("Accessories")
                .cost(new BigDecimal("49.50")).lowStockThreshold(50).build());
        Product prod4 = productRepository.save(Product.builder().name("Podzilla Cover").category("Accessories")
                .cost(new BigDecimal("19.95")).lowStockThreshold(30).build());
        List<Product> products = Arrays.asList(prod1, prod2, prod3, prod4);
        System.out.println("Seeded Products: " + products.size());

        // Couriers
        Courier courier1 = courierRepository
                .save(Courier.builder().name("Speedy Delivery Inc.").status(Courier.CourierStatus.ACTIVE).build());
        Courier courier2 = courierRepository
                .save(Courier.builder().name("Reliable Couriers Co.").status(Courier.CourierStatus.ACTIVE).build());
        Courier courier3 = courierRepository
                .save(Courier.builder().name("Overnight Express").status(Courier.CourierStatus.INACTIVE).build());
        List<Courier> couriers = Arrays.asList(courier1, courier2, courier3);
        System.out.println("Seeded Couriers: " + couriers.size());

        // Customers
        Customer cust1 = customerRepository.save(Customer.builder().name("Alice Smith").build());
        Customer cust2 = customerRepository.save(Customer.builder().name("Bob Johnson").build());
        Customer cust3 = customerRepository.save(Customer.builder().name("Charlie Brown").build());
        List<Customer> customers = Arrays.asList(cust1, cust2, cust3);
        System.out.println("Seeded Customers: " + customers.size());

        // --- Seed Dependent Entities ---

        // Orders and SalesLineItems
        System.out.println("Seeding Orders and SalesLineItems...");
        LocalDate today = LocalDate.now();

        // Order 1 (Completed)
        LocalDateTime placed1 = today.minusDays(10).atTime(9, 30);
        Order order1 = Order.builder()
                .customer(cust1)
                .courier(courier1)
                .region(region1)
                .status(Order.OrderStatus.COMPLETED)
                .orderPlacedTimestamp(placed1)
                .shippedTimestamp(placed1.plusHours(4))
                .deliveredTimestamp(placed1.plusDays(2).plusHours(1))
                .finalStatusTimestamp(placed1.plusDays(2).plusHours(1)) // Same as delivered for COMPLETED
                .numberOfItems(3) // Will be calculated from items
                .totalAmount(BigDecimal.ZERO) // Will be calculated from items
                .courierRating(new BigDecimal("4.5"))
                .build();

        SalesLineItem item1_1 = SalesLineItem.builder().order(order1).product(prod1).quantity(1)
                .pricePerUnit(prod1.getCost()).build();
        SalesLineItem item1_2 = SalesLineItem.builder().order(order1).product(prod3).quantity(2)
                .pricePerUnit(prod3.getCost()).build();
        order1.setSalesLineItems(Arrays.asList(item1_1, item1_2)); // Associate items before saving order due to cascade
        order1.setNumberOfItems(item1_1.getQuantity() + item1_2.getQuantity());
        order1.setTotalAmount(item1_1.getPricePerUnit().multiply(BigDecimal.valueOf(item1_1.getQuantity()))
                .add(item1_2.getPricePerUnit().multiply(BigDecimal.valueOf(item1_2.getQuantity()))));
        orderRepository.save(order1); // CascadeType.ALL on Order.salesLineItems saves the items too

        // Order 2 (Shipped)
        LocalDateTime placed2 = today.minusDays(3).atTime(14, 0);
        Order order2 = Order.builder()
                .customer(cust2)
                .courier(courier2)
                .region(region2)
                .status(Order.OrderStatus.SHIPPED)
                .orderPlacedTimestamp(placed2)
                .shippedTimestamp(placed2.plusDays(1).plusHours(1))
                .finalStatusTimestamp(placed2.plusDays(1).plusHours(1)) // Same as shipped for SHIPPED
                .courierRating(null) // Not rated yet
                .failureReason(null)
                .build();
        SalesLineItem item2_1 = SalesLineItem.builder().order(order2).product(prod2).quantity(1)
                .pricePerUnit(prod2.getCost()).build();
        order2.setSalesLineItems(List.of(item2_1));
        order2.setNumberOfItems(item2_1.getQuantity());
        order2.setTotalAmount(item2_1.getPricePerUnit().multiply(BigDecimal.valueOf(item2_1.getQuantity())));
        orderRepository.save(order2);

        // Order 3 (Failed)
        LocalDateTime placed3 = today.minusDays(5).atTime(11, 15);
        Order order3 = Order.builder()
                .customer(cust1)
                .courier(courier1)
                .region(region3)
                .status(Order.OrderStatus.FAILED)
                .orderPlacedTimestamp(placed3)
                .shippedTimestamp(placed3.plusHours(6))
                .deliveredTimestamp(null)
                .finalStatusTimestamp(placed3.plusDays(3)) // When it was marked failed
                .failureReason("Delivery address incorrect")
                .courierRating(new BigDecimal("2.0"))
                .build();
        SalesLineItem item3_1 = SalesLineItem.builder().order(order3).product(prod4).quantity(1)
                .pricePerUnit(prod4.getCost()).build();
        order3.setSalesLineItems(List.of(item3_1));
        order3.setNumberOfItems(item3_1.getQuantity());
        order3.setTotalAmount(item3_1.getPricePerUnit().multiply(BigDecimal.valueOf(item3_1.getQuantity())));
        orderRepository.save(order3);

        // Order 4 (Completed - Recent)
        LocalDateTime placed4 = today.minusDays(1).atTime(16, 45);
        Order order4 = Order.builder()
                .customer(cust3)
                .courier(courier2)
                .region(region1)
                .status(Order.OrderStatus.COMPLETED)
                .orderPlacedTimestamp(placed4)
                .shippedTimestamp(placed4.plusHours(3))
                .deliveredTimestamp(placed4.plusHours(20))
                .finalStatusTimestamp(placed4.plusHours(20))
                .numberOfItems(2) // Will be calculated
                .totalAmount(BigDecimal.ZERO) // Will be calculated
                .courierRating(new BigDecimal("5.0"))
                .build();

        SalesLineItem item4_1 = SalesLineItem.builder().order(order4).product(prod1).quantity(1)
                .pricePerUnit(prod1.getCost()).build();
        SalesLineItem item4_2 = SalesLineItem.builder().order(order4).product(prod4).quantity(1)
                .pricePerUnit(prod4.getCost()).build();
        order4.setSalesLineItems(Arrays.asList(item4_1, item4_2));
        order4.setNumberOfItems(item4_1.getQuantity() + item4_2.getQuantity());
        order4.setTotalAmount(item4_1.getPricePerUnit().multiply(BigDecimal.valueOf(item4_1.getQuantity()))
                .add(item4_2.getPricePerUnit().multiply(BigDecimal.valueOf(item4_2.getQuantity()))));
        orderRepository.save(order4);

        System.out.println("Seeded Orders: " + orderRepository.count()); // Should be 4

        // Inventory Snapshots
        System.out.println("Seeding Inventory Snapshots...");
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod1).quantity(random.nextInt(50) + prod1.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(5)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod2).quantity(random.nextInt(100) + prod2.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(5)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod3).quantity(random.nextInt(150) + prod3.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(5)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod4).quantity(random.nextInt(80) + prod4.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(5)).build());

        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod1).quantity(random.nextInt(40) + prod1.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(1)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod2).quantity(random.nextInt(90) + prod2.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(1)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod3).quantity(random.nextInt(140) + prod3.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(1)).build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder().product(prod4).quantity(random.nextInt(70) + prod4.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(1)).build());

        System.out.println("Seeded Inventory Snapshots: " + inventorySnapshotRepository.count());

        System.out.println("Database seeding finished.");
    }
}