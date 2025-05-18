package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.models.SalesLineItem;
import com.Podzilla.analytics.api.projections.profit.ProfitByCategoryProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SalesLineItemRepository
 extends JpaRepository<SalesLineItem, UUID> {
    @Query("SELECT sli.product.category as category, "
           + "SUM(sli.quantity * sli.pricePerUnit) as totalRevenue, "
           + "SUM(sli.quantity * sli.product.cost) as totalCost "
           + "FROM SalesLineItem sli "
           + "WHERE sli.order.orderPlacedTimestamp BETWEEN "
           + ":startDate AND :endDate "
           + "AND sli.order.status = 'COMPLETED' "
           + "GROUP BY sli.product.category")
    List<ProfitByCategoryProjection> findSalesByCategoryBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
