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
            + "COALESCE(SUM(CASE WHEN o.finalStatusTimestamp >= :startDate "
            + "AND o.finalStatusTimestamp < :endDate "
            + "AND o.status = 'DELIVERED' THEN oi.quantity * oi.pricePerUnit "
            + "ELSE 0 END), 0) "
            + "AS totalRevenue, "
            + "COALESCE(SUM(CASE WHEN o.finalStatusTimestamp >= :startDate "
            + "AND o.finalStatusTimestamp < :endDate "
            + "AND o.status = 'DELIVERED' THEN oi.quantity ELSE 0 END), 0) "
            + "AS totalUnits "
            + "FROM Product p "
            + "LEFT JOIN p.orderItems oi "
            + "LEFT JOIN oi.order o "
            + "GROUP BY p.id, p.name, p.category "
            + "ORDER BY CASE WHEN :sortBy = 'REVENUE' "
            + "THEN COALESCE(SUM(CASE WHEN o.finalStatusTimestamp >= "
            + ":startDate AND o.finalStatusTimestamp < :endDate "
            + "AND o.status = 'DELIVERED' THEN oi.quantity * oi.pricePerUnit "
            + "ELSE 0 END), 0) "
            + "ELSE COALESCE(SUM(CASE WHEN o.finalStatusTimestamp >= "
            + ":startDate AND o.finalStatusTimestamp < :endDate "
            + "AND o.status = 'DELIVERED' THEN oi.quantity ELSE 0 END), 0) "
            + "END DESC "
            + "LIMIT :limit")
    List<TopSellingProductProjection> findTopSellers(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("limit") Integer limit,
            @Param("sortBy") String sortBy);
}
