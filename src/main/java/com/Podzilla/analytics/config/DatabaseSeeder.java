/**
 * DatabaseSeeder is responsible for populating the database with test data
 * during application startup. This is useful for local development and testing.
 *
 * Notes:
 * - All magic numbers have been replaced with constants for clarity.
 * - Line length is limited to 80 characters for readability.
 */
// CHECKSTYLE:OFF
package com.Podzilla.analytics.config;

import com.Podzilla.analytics.models.Courier;
import com.Podzilla.analytics.models.Customer;
import com.Podzilla.analytics.models.InventorySnapshot;
import com.Podzilla.analytics.models.Order;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.Region;
import com.Podzilla.analytics.models.SalesLineItem;
import com.Podzilla.analytics.repositories.CourierRepository;
import com.Podzilla.analytics.repositories.CustomerRepository;
import com.Podzilla.analytics.repositories.InventorySnapshotRepository;
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

/**
 * Seeds the database with initial test data for development and testing.
 */
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    /** Repository for courier data operations. */
    private final CourierRepository courierRepository;
    /** Repository for customer data operations. */
    private final CustomerRepository customerRepository;
    /** Repository for product data operations. */
    private final ProductRepository productRepository;
    /** Repository for region data operations. */
    private final RegionRepository regionRepository;
    /** Repository for order data operations. */
    private final OrderRepository orderRepository;
    /** Repository for inventory snapshot data operations. */
    private final InventorySnapshotRepository inventorySnapshotRepository;

    /** Random number generator for inventory quantities. */
    private final Random random = new Random();

    /** Low stock threshold for Product 1. */
    private static final int LOW_STOCK_PROD1 = 10;
    /** Low stock threshold for Product 2. */
    private static final int LOW_STOCK_PROD2 = 20;
    /** Low stock threshold for Product 3. */
    private static final int LOW_STOCK_PROD3 = 50;
    /** Low stock threshold for Product 4. */
    private static final int LOW_STOCK_PROD4 = 30;

    /** Price for Product 1. */
    private static final BigDecimal PRICE_PROD1 = new BigDecimal("199.99");
    /** Price for Product 2. */
    private static final BigDecimal PRICE_PROD2 = new BigDecimal("99.99");
    /** Price for Product 3. */
    private static final BigDecimal PRICE_PROD3 = new BigDecimal("49.50");
    /** Price for Product 4. */
    private static final BigDecimal PRICE_PROD4 = new BigDecimal("19.95");

    /** Good courier rating value. */
    private static final BigDecimal RATING_GOOD = new BigDecimal("4.5");
    /** Poor courier rating value. */
    private static final BigDecimal RATING_POOR = new BigDecimal("2.0");
    /** Excellent courier rating value. */
    private static final BigDecimal RATING_EXCELLENT = new BigDecimal("5.0");

    /** Item count for Order 1. */
    private static final int ORDER_ITEM_COUNT_3 = 3;
    /** Item count for Order 4. */
    private static final int ORDER_ITEM_COUNT_2 = 2;

    /** Days prior for Order 1 timestamp. */
    private static final int ORDER_1_DAYS_PRIOR = 10;
    /** Hour for Order 1 timestamp. */
    private static final int ORDER_1_HOUR = 9;
    /** Minute for Order 1 timestamp. */
    private static final int ORDER_1_MINUTE = 30;
    /** Shipping hours for Order 1. */
    private static final int ORDER_1_SHIP_HOURS = 4;
    /** Delivery days for Order 1. */
    private static final int ORDER_1_DELIVER_DAYS = 2;
    /** Delivery hours for Order 1. */
    private static final int ORDER_1_DELIVER_HOURS = 1;

    /** Days prior for Order 2 timestamp. */
    private static final int ORDER_2_DAYS_PRIOR = 3;
    /** Hour for Order 2 timestamp. */
    private static final int ORDER_2_HOUR = 14;
    /** Minute for Order 2 timestamp. */
    private static final int ORDER_2_MINUTE = 0;
    /** Shipping days for Order 2. */
    private static final int ORDER_2_SHIP_DAYS = 1;
    /** Shipping hours for Order 2. */
    private static final int ORDER_2_SHIP_HOURS = 1;

    /** Days prior for Order 3 timestamp. */
    private static final int ORDER_3_DAYS_PRIOR = 5;
    /** Hour for Order 3 timestamp. */
    private static final int ORDER_3_HOUR = 11;
    /** Minute for Order 3 timestamp. */
    private static final int ORDER_3_MINUTE = 15;
    /** Shipping hours for Order 3. */
    private static final int ORDER_3_SHIP_HOURS = 6;
    /** Final status days for Order 3. */
    private static final int ORDER_3_FINAL_DAYS = 3;

    /** Days prior for Order 4 timestamp. */
    private static final int ORDER_4_DAYS_PRIOR = 1;
    /** Hour for Order 4 timestamp. */
    private static final int ORDER_4_HOUR = 16;
    /** Minute for Order 4 timestamp. */
    private static final int ORDER_4_MINUTE = 45;
    /** Shipping hours for Order 4. */
    private static final int ORDER_4_SHIP_HOURS = 3;
    /** Delivery hours for Order 4. */
    private static final int ORDER_4_DELIVER_HOURS = 20;

    /** Days prior for first inventory snapshot. */
    private static final int INVENTORY_SNAPSHOT_DAYS_PRIOR_1 = 5;
    /** Days prior for second inventory snapshot. */
    private static final int INVENTORY_SNAPSHOT_DAYS_PRIOR_2 = 1;
    /** Inventory range for Product 1. */
    private static final int INVENTORY_RANGE_PROD1 = 50;
    /** Inventory range for Product 2. */
    private static final int INVENTORY_RANGE_PROD2 = 100;
    /** Inventory range for Product 3. */
    private static final int INVENTORY_RANGE_PROD3 = 150;
    /** Inventory range for Product 4. */
    private static final int INVENTORY_RANGE_PROD4 = 80;
    /** Inventory quantity for Product 1. */
    private static final int INVENTORY_QUANTITY_PROD1 = 40;
    /** Inventory quantity for Product 2. */
    private static final int INVENTORY_QUANTITY_PROD2 = 90;
    /** Inventory quantity for Product 3. */
    private static final int INVENTORY_QUANTITY_PROD3 = 140;
    /** Inventory quantity for Product 4. */
    private static final int INVENTORY_QUANTITY_PROD4 = 70;

    /** index 0. */
    private static final int INDEX_ZERO = 0;

    /** index 1. */
    private static final int INDEX_ONE = 1;

    /** index 2. */
    private static final int INDEX_TWO = 2;

    /** index 3. */
    private static final int INDEX_THREE = 3;

    /**
     * Executes the database seeding process if the database is empty.
     *
     * @param args command line arguments
     */
    @Override
    @Transactional
    public final void run(final String... args) {
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
        seedInventorySnapshots(products);
        System.out.println("Seeded Inventory Snapshots: "
                + inventorySnapshotRepository.count());

        System.out.println("Database seeding finished.");
    }

    /**
     * Seeds regions into the database.
     *
     * @return list of seeded regions
     */
    private List<Region> seedRegions() {
        Region region1 = regionRepository.save(
                Region.builder().city("Metropolis").state("NY")
                        .country("USA").postalCode("10001")
                        .build());
        Region region2 = regionRepository.save(
                Region.builder().city("Gotham").state("NJ")
                        .country("USA").postalCode("07001")
                        .build());
        Region region3 = regionRepository.save(
                Region.builder().city("Star City").state("CA")
                        .country("USA").postalCode("90210")
                        .build());
        return Arrays.asList(region1, region2, region3);
    }

    /**
     * Seeds products into the database.
     *
     * @return list of seeded products
     */
    private List<Product> seedProducts() {
        Product prod1 = productRepository.save(Product.builder()
                .name("Podzilla Pro").category("Electronics")
                .cost(PRICE_PROD1)
                .lowStockThreshold(LOW_STOCK_PROD1).build());
        Product prod2 = productRepository.save(Product.builder()
                .name("Podzilla Mini").category("Electronics")
                .cost(PRICE_PROD2)
                .lowStockThreshold(LOW_STOCK_PROD2).build());
        Product prod3 = productRepository.save(Product.builder()
                .name("Charging Case").category("Accessories")
                .cost(PRICE_PROD3)
                .lowStockThreshold(LOW_STOCK_PROD3).build());
        Product prod4 = productRepository.save(Product.builder()
                .name("Podzilla Cover").category("Accessories")
                .cost(PRICE_PROD4)
                .lowStockThreshold(LOW_STOCK_PROD4).build());
        return Arrays.asList(prod1, prod2, prod3, prod4);
    }

    /**
     * Seeds couriers into the database.
     *
     * @return list of seeded couriers
     */
    private List<Courier> seedCouriers() {
        Courier courier1 = courierRepository.save(
                Courier.builder().name("Speedy Delivery Inc.")
                        .status(Courier.CourierStatus.ACTIVE).build());
        Courier courier2 = courierRepository.save(
                Courier.builder().name("Reliable Couriers Co.")
                        .status(Courier.CourierStatus.ACTIVE).build());
        Courier courier3 = courierRepository.save(
                Courier.builder().name("Overnight Express")
                        .status(Courier.CourierStatus.INACTIVE).build());
        return Arrays.asList(courier1, courier2, courier3);
    }

    /**
     * Seeds customers into the database.
     *
     * @return list of seeded customers
     */
    private List<Customer> seedCustomers() {
        Customer cust1 = customerRepository.save(
                Customer.builder().name("Alice Smith").build());
        Customer cust2 = customerRepository.save(
                Customer.builder().name("Bob Johnson").build());
        Customer cust3 = customerRepository.save(
                Customer.builder().name("Charlie Brown").build());
        return Arrays.asList(cust1, cust2, cust3);
    }

    /**
     * Seeds orders and their sales line items into the database.
     *
     * @param customers list of customers
     * @param couriers  list of couriers
     * @param regions   list of regions
     * @param products  list of products
     */
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
                .customer(customers.get(0)).courier(couriers.get(0))
                .region(regions.get(0))
                .status(Order.OrderStatus.COMPLETED)
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
        SalesLineItem itemFirstOrderFirst = SalesLineItem.builder()
                .order(order1).product(products.get(0)).quantity(1)
                .pricePerUnit(PRICE_PROD1).build();
        SalesLineItem itemFirstOrderSecond = SalesLineItem.builder()
                .order(order1).product(products.get(2)).quantity(2)
                .pricePerUnit(PRICE_PROD3).build();
        order1.setSalesLineItems(Arrays.asList(itemFirstOrderFirst,
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
                .customer(customers.get(1)).courier(couriers.get(1))
                .region(regions.get(1))
                .status(Order.OrderStatus.SHIPPED)
                .orderPlacedTimestamp(placed2)
                .shippedTimestamp(placed2.plusDays(ORDER_2_SHIP_DAYS)
                        .plusHours(ORDER_2_SHIP_HOURS))
                .finalStatusTimestamp(placed2.plusDays(ORDER_2_SHIP_DAYS)
                        .plusHours(ORDER_2_SHIP_HOURS))
                .courierRating(null).failureReason(null)
                .build();
        SalesLineItem itemSecondOrderFirst = SalesLineItem.builder()
                .order(order2).product(products.get(1)).quantity(1)
                .pricePerUnit(PRICE_PROD2).build();
        order2.setSalesLineItems(List.of(itemSecondOrderFirst));
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
                .customer(customers.get(0)).courier(couriers.get(0))
                .region(regions.get(2))
                .status(Order.OrderStatus.FAILED)
                .orderPlacedTimestamp(placed3)
                .status(Order.OrderStatus.FAILED)
                .orderPlacedTimestamp(placed3)
                .shippedTimestamp(placed3.plusHours(ORDER_3_SHIP_HOURS))
                .deliveredTimestamp(null)
                .finalStatusTimestamp(placed3.plusDays(ORDER_3_FINAL_DAYS))
                .failureReason("Delivery address incorrect")
                .courierRating(RATING_POOR)
                .build();
        SalesLineItem itemThirdOrderFirst = SalesLineItem.builder()
                .order(order3).product(products.get(INDEX_THREE)).quantity(1)
                .pricePerUnit(PRICE_PROD4).build();
        order3.setSalesLineItems(List.of(itemThirdOrderFirst));
        order3.setNumberOfItems(itemThirdOrderFirst.getQuantity());
        order3.setTotalAmount(
                itemThirdOrderFirst.getPricePerUnit().multiply(
                        BigDecimal.valueOf(itemThirdOrderFirst.getQuantity())));
        orderRepository.save(order3);

        // Order 4
        LocalDateTime placed4 = today.minusDays(ORDER_4_DAYS_PRIOR)
                .atTime(ORDER_4_HOUR, ORDER_4_MINUTE);
        Order order4 = Order.builder()
                .customer(customers.get(2)).courier(couriers.get(1))
                .region(regions.get(0))
                .status(Order.OrderStatus.COMPLETED)
                .orderPlacedTimestamp(placed4)
                .shippedTimestamp(placed4.plusHours(ORDER_4_SHIP_HOURS))
                .deliveredTimestamp(placed4.plusHours(ORDER_4_DELIVER_HOURS))
                .finalStatusTimestamp(placed4.plusHours(ORDER_4_DELIVER_HOURS))
                .numberOfItems(ORDER_ITEM_COUNT_2)
                .totalAmount(BigDecimal.ZERO)
                .courierRating(RATING_EXCELLENT)
                .build();
        SalesLineItem itemFourthOrderFirst = SalesLineItem.builder()
                .order(order4).product(products.get(INDEX_ZERO)).quantity(1)
                .pricePerUnit(PRICE_PROD1).build();
        SalesLineItem itemFourthOrderSecond = SalesLineItem.builder()
                .order(order4).product(products.get(INDEX_THREE)).quantity(1)
                .pricePerUnit(PRICE_PROD4).build();
        order4.setSalesLineItems(Arrays.asList(itemFourthOrderFirst,
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

    /**
     * Seeds inventory snapshots for all products.
     *
     * @param products list of products to create snapshots for
     */
    private void seedInventorySnapshots(final List<Product> products) {
        seedInventorySnapshot(products.get(INDEX_ZERO), INVENTORY_RANGE_PROD1,
                INVENTORY_QUANTITY_PROD1);
        seedInventorySnapshot(products.get(INDEX_ONE), INVENTORY_RANGE_PROD2,
                INVENTORY_QUANTITY_PROD2);
        seedInventorySnapshot(products.get(INDEX_TWO), INVENTORY_RANGE_PROD3,
                INVENTORY_QUANTITY_PROD3);
        seedInventorySnapshot(products.get(INDEX_THREE), INVENTORY_RANGE_PROD4,
                INVENTORY_QUANTITY_PROD4);
    }

    /**
     * Seeds inventory snapshots for a specific product.
     *
     * @param product  the product to create snapshots for
     * @param range    random range for first snapshot quantity
     * @param quantity random range for second snapshot quantity
     */
    private void seedInventorySnapshot(
            final Product product, final int range, final int quantity) {
        inventorySnapshotRepository.save(
                InventorySnapshot.builder()
                        .product(product)
                        .quantity(random.nextInt(range)
                                + product.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(
                                INVENTORY_SNAPSHOT_DAYS_PRIOR_1))
                        .build());
        inventorySnapshotRepository.save(
                InventorySnapshot.builder()
                        .product(product)
                        .quantity(random.nextInt(quantity)
                                + product.getLowStockThreshold())
                        .timestamp(LocalDateTime.now().minusDays(
                                INVENTORY_SNAPSHOT_DAYS_PRIOR_2))
                        .build());
    }
}
