package com.Podzilla.analytics.config;

import com.Podzilla.analytics.models.Courier;
import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.ProductSnapshot;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.models.OrderItem;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.ProductSnapshotRepository;
import com.Podzilla.analytics.repositories.OrderRepository;
import com.Podzilla.analytics.repositories.ProductRepository;
import com.Podzilla.analytics.repositories.RegionRepository;
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
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final RegionRepository regionRepository;
    private final OrderRepository orderRepository;
    private final ProductSnapshotRepository productSnapshotRepository;

    private final Random random = new Random();
    private static final int LOW_STOCK_PROD1 = 10;
    private static final int LOW_STOCK_PROD2 = 20;
    private static final int LOW_STOCK_PROD3 = 50;
    private static final int LOW_STOCK_PROD4 = 30;

    private static final BigDecimal PRICE_PROD1 = new BigDecimal("199.99");
    private static final BigDecimal PRICE_PROD2 = new BigDecimal("99.99");
    private static final BigDecimal PRICE_PROD3 = new BigDecimal("49.50");
    private static final BigDecimal PRICE_PROD4 = new BigDecimal("19.95");

    private static final BigDecimal RATING_GOOD = new BigDecimal("4.5");
    private static final BigDecimal RATING_POOR = new BigDecimal("2.0");
    private static final BigDecimal RATING_EXCELLENT = new BigDecimal("5.0");

    private static final int ORDER_ITEM_COUNT_3 = 3;
    private static final int ORDER_ITEM_COUNT_2 = 2;

    private static final int ORDER_1_DAYS_PRIOR = 10;
    private static final int ORDER_1_HOUR = 9;
    private static final int ORDER_1_MINUTE = 30;
    private static final int ORDER_1_SHIP_HOURS = 4;
    private static final int ORDER_1_DELIVER_DAYS = 2;
    private static final int ORDER_1_DELIVER_HOURS = 1;

    private static final int ORDER_2_DAYS_PRIOR = 3;
    private static final int ORDER_2_HOUR = 14;
    private static final int ORDER_2_MINUTE = 0;
    private static final int ORDER_2_SHIP_DAYS = 1;
    private static final int ORDER_2_SHIP_HOURS = 1;

    private static final int ORDER_3_DAYS_PRIOR = 5;
    private static final int ORDER_3_HOUR = 11;
    private static final int ORDER_3_MINUTE = 15;
    private static final int ORDER_3_SHIP_HOURS = 6;
    private static final int ORDER_3_FINAL_DAYS = 3;

    private static final int ORDER_4_DAYS_PRIOR = 1;
    private static final int ORDER_4_HOUR = 16;
    private static final int ORDER_4_MINUTE = 45;
    private static final int ORDER_4_SHIP_HOURS = 3;
    private static final int ORDER_4_DELIVER_HOURS = 20;

    private static final int INVENTORY_SNAPSHOT_DAYS_PRIOR_1 = 5;
    private static final int INVENTORY_SNAPSHOT_DAYS_PRIOR_2 = 1;
    private static final int INVENTORY_RANGE_PROD1 = 50;
    private static final int INVENTORY_RANGE_PROD2 = 100;
    private static final int INVENTORY_RANGE_PROD3 = 150;
    private static final int INVENTORY_RANGE_PROD4 = 80;
    private static final int INVENTORY_QUANTITY_PROD1 = 40;
    private static final int INVENTORY_QUANTITY_PROD2 = 90;
    private static final int INVENTORY_QUANTITY_PROD3 = 140;
    private static final int INVENTORY_QUANTITY_PROD4 = 70;

    private static final int INDEX_THREE = 3;

    @Override
    @Transactional
    public void run(final String... args) {
        System.out.println("Checking if database needs seeding...");

        if (courierRepository.count() > 0) {
            System.out.println("Database already seeded. Skipping.");
            return;
        }

        System.out.println("Seeding database...");

        List<Region> regions = seedRegions();
        System.out.println("Seeded Regions: " + regions.size());

        List<Product> products = seedProducts();
        System.out.println("Seeded Products: " + products.size());

        List<Courier> couriers = seedCouriers();
        System.out.println("Seeded Couriers: " + couriers.size());

        List<Customer> customers = seedCustomers();
        System.out.println("Seeded Customers: " + customers.size());

        System.out.println("Seeding Orders and SalesLineItems...");
        seedOrders(customers, couriers, regions, products);
        System.out.println("Seeded Orders: " + orderRepository.count());

        System.out.println("Seeding Inventory Snapshots...");
        seedProductSnapshots(products);
        System.out.println("Seeded Product Snapshots: "
                + productSnapshotRepository.count());

        System.out.println("Database seeding finished.");
    }

    private List<Region> seedRegions() {
        Region region1 = regionRepository.save(
                Region.builder()
                        // .id(UUID.randomUUID())
                        .city("Metropolis").state("NY")
                        .country("USA").postalCode("10001")
                        .build());
        Region region2 = regionRepository.save(
                Region.builder()
                        // .id(UUID.randomUUID())
                        .city("Gotham").state("NJ")
                        .country("USA").postalCode("07001")
                        .build());
        Region region3 = regionRepository.save(
                Region.builder()
                        // .id(UUID.randomUUID())
                        .city("Star City").state("CA")
                        .country("USA").postalCode("90210")
                        .build());
        return Arrays.asList(region1, region2, region3);
    }

    private List<Product> seedProducts() {
        Product prod1 = productRepository.save(Product.builder()
                .id(UUID.randomUUID())
                .name("Podzilla Pro").category("Electronics")
                .cost(PRICE_PROD1)
                .lowStockThreshold(LOW_STOCK_PROD1).build());
        Product prod2 = productRepository.save(Product.builder()
                .id(UUID.randomUUID())
                .name("Podzilla Mini").category("Electronics")
                .cost(PRICE_PROD2)
                .lowStockThreshold(LOW_STOCK_PROD2).build());
        Product prod3 = productRepository.save(Product.builder()
                .id(UUID.randomUUID())
                .name("Charging Case").category("Accessories")
                .cost(PRICE_PROD3)
                .lowStockThreshold(LOW_STOCK_PROD3).build());
        Product prod4 = productRepository.save(Product.builder()
                .id(UUID.randomUUID())
                .name("Podzilla Cover").category("Accessories")
                .cost(PRICE_PROD4)
                .lowStockThreshold(LOW_STOCK_PROD4).build());
        return Arrays.asList(prod1, prod2, prod3, prod4);
    }

    private List<Courier> seedCouriers() {
        Courier courier1 = courierRepository.save(
                Courier.builder()
                        .id(UUID.randomUUID())
                        .name("Speedy Delivery Inc.").build());
        Courier courier2 = courierRepository.save(
                Courier.builder()
                        .id(UUID.randomUUID())
                        .name("Reliable Couriers Co.").build());
        Courier courier3 = courierRepository.save(
                Courier.builder()
                        .id(UUID.randomUUID())
                        .name("Overnight Express").build());
        return Arrays.asList(courier1, courier2, courier3);
    }

    private List<Customer> seedCustomers() {
        Customer cust1 = customerRepository.save(
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Alice Smith").build());
        Customer cust2 = customerRepository.save(
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Bob Johnson").build());
        Customer cust3 = customerRepository.save(
                Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Charlie Brown").build());
        return Arrays.asList(cust1, cust2, cust3);
    }

    private void seedOrders(
            final List<Customer> customers,
            final List<Courier> couriers,
            final List<Region> regions,
            final List<Product> products) {
        LocalDate today = LocalDate.now();

        // Order 1
        LocalDateTime placed1 = today.minusDays(ORDER_1_DAYS_PRIOR)
                .atTime(ORDER_1_HOUR, ORDER_1_MINUTE);
        Order order1 = Order.builder()
                .id(UUID.randomUUID())
                .customer(customers.get(0)).courier(couriers.get(0))
                .region(regions.get(0))
                .status(Order.OrderStatus.DELIVERED)
                .orderPlacedTimestamp(placed1)
                .shippedTimestamp(placed1.plusHours(ORDER_1_SHIP_HOURS))
                .deliveredTimestamp(placed1.plusDays(ORDER_1_DELIVER_DAYS)
                        .plusHours(ORDER_1_DELIVER_HOURS))
                .finalStatusTimestamp(placed1.plusDays(ORDER_1_DELIVER_DAYS)
                        .plusHours(ORDER_1_DELIVER_HOURS))
                .numberOfItems(ORDER_ITEM_COUNT_3)
                .totalAmount(BigDecimal.ZERO)
                .courierRating(RATING_GOOD)
                .build();
        OrderItem itemFirstOrderFirst = OrderItem.builder()
                .order(order1).product(products.get(0)).quantity(1)
                .pricePerUnit(PRICE_PROD1).build();
        OrderItem itemFirstOrderSecond = OrderItem.builder()
                .order(order1).product(products.get(2)).quantity(2)
                .pricePerUnit(PRICE_PROD3).build();
        order1.setOrderItems(Arrays.asList(itemFirstOrderFirst,
                itemFirstOrderSecond));
        order1.setNumberOfItems(itemFirstOrderFirst.getQuantity()
                + itemFirstOrderSecond.getQuantity());
        order1.setTotalAmount(
                itemFirstOrderFirst.getPricePerUnit().multiply(
                        BigDecimal.valueOf(itemFirstOrderFirst.getQuantity()))
                        .add(itemFirstOrderSecond.getPricePerUnit().multiply(
                                BigDecimal.valueOf(
                                        itemFirstOrderSecond.getQuantity()))));
        orderRepository.save(order1);

        // Order 2
        LocalDateTime placed2 = today.minusDays(ORDER_2_DAYS_PRIOR)
                .atTime(ORDER_2_HOUR, ORDER_2_MINUTE);
        Order order2 = Order.builder()
                .id(UUID.randomUUID())
                .customer(customers.get(1)).courier(couriers.get(1))
                .region(regions.get(1))
                .status(Order.OrderStatus.DELIVERED)
                .orderPlacedTimestamp(placed2)
                .shippedTimestamp(placed2.plusDays(ORDER_2_SHIP_DAYS)
                        .plusHours(ORDER_2_SHIP_HOURS))
                .finalStatusTimestamp(placed2.plusDays(ORDER_2_SHIP_DAYS)
                        .plusHours(ORDER_2_SHIP_HOURS))
                .courierRating(null).failureReason(null)
                .build();
        OrderItem itemSecondOrderFirst = OrderItem.builder()
                .order(order2).product(products.get(1)).quantity(1)
                .pricePerUnit(PRICE_PROD2).build();
        order2.setOrderItems(List.of(itemSecondOrderFirst));
        order2.setNumberOfItems(itemSecondOrderFirst.getQuantity());
        order2.setTotalAmount(
                itemSecondOrderFirst.getPricePerUnit().multiply(
                        BigDecimal.valueOf(itemSecondOrderFirst
                                .getQuantity())));
        orderRepository.save(order2);

        // Order 3
        LocalDateTime placed3 = today.minusDays(ORDER_3_DAYS_PRIOR)
                .atTime(ORDER_3_HOUR, ORDER_3_MINUTE);
        Order order3 = Order.builder()
                .id(UUID.randomUUID())
                .customer(customers.get(0)).courier(couriers.get(0))
                .region(regions.get(2))
                .orderPlacedTimestamp(placed3)
                .status(Order.OrderStatus.DELIVERED)
                .orderPlacedTimestamp(placed3)
                .shippedTimestamp(placed3.plusHours(ORDER_3_SHIP_HOURS))
                .deliveredTimestamp(null)
                .finalStatusTimestamp(placed3.plusDays(ORDER_3_FINAL_DAYS))
                .failureReason("Delivery address incorrect")
                .courierRating(RATING_POOR)
                .build();
        OrderItem itemThirdOrderFirst = OrderItem.builder()
                .order(order3).product(products.get(INDEX_THREE)).quantity(1)
                .pricePerUnit(PRICE_PROD4).build();
        order3.setOrderItems(List.of(itemThirdOrderFirst));
        order3.setNumberOfItems(itemThirdOrderFirst.getQuantity());
        order3.setTotalAmount(
                itemThirdOrderFirst.getPricePerUnit().multiply(
                        BigDecimal.valueOf(itemThirdOrderFirst.getQuantity())));
        orderRepository.save(order3);

        // Order 4
        LocalDateTime placed4 = today.minusDays(ORDER_4_DAYS_PRIOR)
                .atTime(ORDER_4_HOUR, ORDER_4_MINUTE);
        Order order4 = Order.builder()
                .id(UUID.randomUUID())
                .customer(customers.get(2)).courier(couriers.get(1))
                .region(regions.get(0))
                .status(Order.OrderStatus.DELIVERED)
                .orderPlacedTimestamp(placed4)
                .shippedTimestamp(placed4.plusHours(ORDER_4_SHIP_HOURS))
                .deliveredTimestamp(placed4.plusHours(ORDER_4_DELIVER_HOURS))
                .finalStatusTimestamp(placed4.plusHours(ORDER_4_DELIVER_HOURS))
                .numberOfItems(ORDER_ITEM_COUNT_2)
                .totalAmount(BigDecimal.ZERO)
                .courierRating(RATING_EXCELLENT)
                .build();
        OrderItem itemFourthOrderFirst = OrderItem.builder()
                .order(order4).product(products.get(0)).quantity(1)
                .pricePerUnit(PRICE_PROD1).build();
        OrderItem itemFourthOrderSecond = OrderItem.builder()
                .order(order4).product(products.get(INDEX_THREE)).quantity(1)
                .pricePerUnit(PRICE_PROD4).build();
        order4.setOrderItems(Arrays.asList(itemFourthOrderFirst,
                itemFourthOrderSecond));
        order4.setNumberOfItems(
                itemFourthOrderFirst.getQuantity() + itemFourthOrderSecond
                        .getQuantity());
        order4.setTotalAmount(
                itemFourthOrderFirst.getPricePerUnit().multiply(
                        BigDecimal.valueOf(itemFourthOrderFirst.getQuantity()))
                        .add(itemFourthOrderSecond.getPricePerUnit().multiply(
                                BigDecimal.valueOf(itemFourthOrderSecond
                                        .getQuantity()))));
        orderRepository.save(order4);
    }

    private void seedProductSnapshots(final List<Product> products) {
        seedProductSnapshot(products.get(0), INVENTORY_RANGE_PROD1,
                INVENTORY_QUANTITY_PROD1);
        seedProductSnapshot(products.get(1), INVENTORY_RANGE_PROD2,
                INVENTORY_QUANTITY_PROD2);
        seedProductSnapshot(products.get(2), INVENTORY_RANGE_PROD3,
                INVENTORY_QUANTITY_PROD3);
        seedProductSnapshot(products.get(INDEX_THREE), INVENTORY_RANGE_PROD4,
                INVENTORY_QUANTITY_PROD4);
    }

    private void seedProductSnapshot(
            final Product product, final int range, final int quantity) {
        productSnapshotRepository.save(
                ProductSnapshot.builder()
                        .product(product)
                        .quantity(random.nextInt(range)
                                + product.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(
                                INVENTORY_SNAPSHOT_DAYS_PRIOR_1))
                        .build());
        productSnapshotRepository.save(
                ProductSnapshot.builder()
                        .product(product)
                        .quantity(random.nextInt(quantity)
                                + product.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(
                                INVENTORY_SNAPSHOT_DAYS_PRIOR_2))
                        .build());
    }
}
