package com.Podzilla.analytics.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.product.TopSellingProductProjection;
import com.Podzilla.analytics.models.Product;
import java.util.UUID;
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p.id AS id, "
            + "p.name AS name, "
            + "p.category AS category, "
            + "SUM(oi.quantity * oi.pricePerUnit) AS totalRevenue, "
            + "SUM(oi.quantity) AS totalUnits "
            + "FROM OrderItem oi "
            + "JOIN oi.order o "
            + "JOIN oi.product p "
            + "WHERE o.finalStatusTimestamp >= :startDate "
            + "AND o.finalStatusTimestamp <  :endDate "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY p.id, p.name, p.category "
            + "ORDER BY CASE WHEN :sortBy = 'REVENUE' "
            + "THEN SUM(oi.quantity * oi.pricePerUnit) "
            + "              WHEN :sortBy = 'UNITS'   THEN SUM(oi.quantity) "
            + "              ELSE SUM(oi.quantity * oi.pricePerUnit) END DESC")
    List<TopSellingProductProjection> findTopSellers(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("limit") Integer limit,
            @Param("sortBy") String sortBy // Pass the enum name as a String
    );
}
