package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                p.id,
                p.name,
                p.category,
                SUM(sli.quantity * sli.price_per_unit) AS total_revenue,
                SUM(sli.quantity) AS total_units
            FROM
                orders o
            JOIN
                sales_line_items sli ON o.id = sli.order_id
            JOIN
                products p ON sli.product_id = p.id
            WHERE
                o.order_placed_timestamp >= :startDate
                AND o.order_placed_timestamp < :endDate
                AND o.status = 'COMPLETED'
            GROUP BY
                p.id, p.name, p.category
            ORDER BY
                CASE :sortBy
                    WHEN 'REVENUE' THEN SUM(sli.quantity * sli.price_per_unit)
                    WHEN 'UNITS' THEN SUM(sli.quantity)
                    ELSE SUM(sli.quantity * sli.price_per_unit)
                END DESC,
                CASE :sortBy
                    WHEN 'REVENUE' THEN SUM(sli.quantity)
                    WHEN 'UNITS' THEN SUM(sli.quantity * sli.price_per_unit)
                    ELSE SUM(sli.quantity)
                END DESC
            LIMIT COALESCE(:limit , 10)

                     """, nativeQuery = true) // Use nativeQuery = true for table names and database functions

    List<TopSellingProductProjection> findTopSellers(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("limit") Integer limit,
            @Param("sortBy") String sortBy // Pass the enum name as a String
    );
}