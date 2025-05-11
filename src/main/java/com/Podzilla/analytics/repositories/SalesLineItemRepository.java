package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.models.SalesLineItem;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesLineItemRepository extends JpaRepository<SalesLineItem, Long> {
    
    @Query("SELECT sli.product.category as category, "
           + "SUM(sli.quantity * sli.pricePerUnit) as totalRevenue, "
           + "SUM(sli.quantity * sli.product.cost) as totalCost "
           + "FROM SalesLineItem sli "
           + "WHERE sli.order.orderPlacedTimestamp BETWEEN :startDate AND :endDate "
           + "AND sli.order.status = 'COMPLETED' "
           + "GROUP BY sli.product.category")
    List<Object[]> findSalesByCategoryBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
