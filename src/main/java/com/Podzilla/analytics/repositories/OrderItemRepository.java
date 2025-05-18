package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.models.OrderItem;
import com.Podzilla.analytics.api.projections.profit.ProfitByCategoryProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository
 extends JpaRepository<OrderItem, UUID> {
    @Query("SELECT oi.product.category as category, "
           + "SUM(oi.quantity * oi.pricePerUnit) as totalRevenue, "
           + "SUM(oi.quantity * oi.product.cost) as totalCost "
           + "FROM OrderItem oi "
           + "WHERE oi.order.finalStatusTimestamp BETWEEN "
           + ":startDate AND :endDate "
           + "AND oi.order.status = 'DELIVERED' "
           + "GROUP BY oi.product.category")
    List<ProfitByCategoryProjection> findSalesByCategoryBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
