package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


     @Query(value = """
        SELECT
            CASE :reportPeriod
                WHEN 'DAILY' THEN DATE(o.order_placed_timestamp) -- Adjust date functions for your DB
                WHEN 'WEEKLY' THEN DATE_TRUNC('week', o.order_placed_timestamp) -- Adjust date functions for your DB
                WHEN 'MONTHLY' THEN DATE_TRUNC('month', o.order_placed_timestamp) -- Adjust date functions for your DB
            END,
            SUM(o.total_amount)
        FROM
            orders o
        WHERE
            o.order_placed_timestamp >= :startDate
            AND o.order_placed_timestamp < :endDate
            AND o.status IN ('COMPLETED')
        GROUP BY
            CASE :reportPeriod
                WHEN 'DAILY' THEN DATE(o.order_placed_timestamp) -- Adjust date functions for your DB
                WHEN 'WEEKLY' THEN DATE_TRUNC('week', o.order_placed_timestamp) -- Adjust date functions for your DB
                WHEN 'MONTHLY' THEN DATE_TRUNC('month', o.order_placed_timestamp) -- Adjust date functions for your DB
            END
        ORDER BY
            1 -- Order by the first selected column (period_start_date)
        """, nativeQuery = true) // Use nativeQuery = true for database-specific functions
    List<Object[]> findRevenueSummaryByPeriod(
        @Param("startDate") LocalDate startDate, // Use Date type matching your DTO/entity
        @Param("endDate") LocalDate endDate,   // Use Date type matching your DTO/entity
        @Param("reportPeriod") String reportPeriod // Pass the period as a String
    );
}