package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.TopSellingProductProjection;
import com.Podzilla.analytics.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

     // Query to find top-selling products by revenue or units
     @Query(value = """
         SELECT
             p.id,                             -- Product ID
             p.name,                           -- Product Name
             p.category,                       -- Product Category
             SUM(sli.quantity * sli.price_per_unit) AS total_revenue, -- Calculate total revenue for the product
             SUM(sli.quantity) AS total_units       -- Calculate total units sold for the product
         FROM
             orders o
         JOIN
             sales_line_items sli ON o.id = sli.orderId -- Join orders with line items (assuming order entity has 'id')
         JOIN
             products p ON sli.productId = p.id   -- Join line items with products
         WHERE
             o.order_placed_timestamp >= :startDate
             AND o.order_placed_timestamp < :endDate
             AND o.status IN ('COMPLETED') -- Filter for completed orders
         GROUP BY
             p.id, p.name, p.category -- Group by product details
         ORDER BY
             CASE :sortBy -- Order conditionally based on the sortBy parameter
                 WHEN 'REVENUE' THEN SUM(sli.quantity * sli.price_per_unit) -- Order by calculated revenue
                 WHEN 'UNITS' THEN SUM(sli.quantity)     -- Order by calculated units
                 ELSE SUM(sli.quantity * sli.price_per_unit) -- Default sort if sortBy is null or invalid
             END DESC -- Order in descending order for "top" sellers
         LIMIT :limit -- Apply the limit
         """, nativeQuery = true) // Use nativeQuery = true for table names and database functions

     List<TopSellingProductProjection> findTopSellers(
         @Param("startDate") LocalDate startDate,
         @Param("endDate") LocalDate endDate,
         @Param("limit") Integer limit,
         @Param("sortBy") String sortBy // Pass the enum name as a String
     );
}