package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.fulfillment.FulfillmentTimeProjection;
import com.Podzilla.analytics.api.projections.order.OrderFailureRateProjection;
import com.Podzilla.analytics.api.projections.order.OrderFailureReasonsProjection;
import com.Podzilla.analytics.api.projections.order.OrderRegionProjection;
import com.Podzilla.analytics.api.projections.order.OrderStatusProjection;
import com.Podzilla.analytics.api.projections.revenue.RevenueByCategoryProjection;
import com.Podzilla.analytics.api.projections.revenue.RevenueSummaryProjection;
import com.Podzilla.analytics.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT 'OVERALL' AS groupByValue, "
            + "AVG( (EXTRACT(EPOCH FROM o.shippedTimestamp) - "
            + "EXTRACT(EPOCH FROM o.orderPlacedTimestamp)) / 3600) "
            + "AS averageDuration "
            + "FROM Order o "
            + "WHERE o.orderPlacedTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.shippedTimestamp IS NOT NULL")
    FulfillmentTimeProjection findPlaceToShipTimeOverall(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT CONCAT('RegionID_', r.id) AS groupByValue, "
            + "AVG( (EXTRACT(EPOCH FROM o.shippedTimestamp) - "
            + "EXTRACT(EPOCH FROM o.orderPlacedTimestamp)) / 3600) "
            + "AS averageDuration "
            + "FROM Order o "
            + "JOIN o.region r "
            + "WHERE o.orderPlacedTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.shippedTimestamp IS NOT NULL "
            + "GROUP BY r.id")
    List<FulfillmentTimeProjection> findPlaceToShipTimeByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT 'OVERALL' AS groupByValue, "
            + "AVG( (EXTRACT(EPOCH FROM o.deliveredTimestamp) - "
            + "EXTRACT(EPOCH FROM o.shippedTimestamp)) / 3600) "
            + "AS averageDuration "
            + "FROM Order o "
            + "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.deliveredTimestamp IS NOT NULL "
            + "AND o.status = 'DELIVERED'")
    FulfillmentTimeProjection findShipToDeliverTimeOverall(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT CONCAT('RegionID_', r.id) AS groupByValue, "
            + "AVG( (EXTRACT(EPOCH FROM o.deliveredTimestamp) - "
            + "EXTRACT(EPOCH FROM o.shippedTimestamp)) / 3600) "
            + "AS averageDuration "
            + "FROM Order o "
            + "JOIN o.region r "
            + "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.deliveredTimestamp IS NOT NULL "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY r.id")
    List<FulfillmentTimeProjection> findShipToDeliverTimeByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT CONCAT('CourierID_', c.id) AS groupByValue, "
            + "AVG( (EXTRACT(EPOCH FROM o.deliveredTimestamp) - "
            + "EXTRACT(EPOCH FROM o.shippedTimestamp)) / 3600) "
            + "AS averageDuration "
            + "FROM Order o "
            + "JOIN o.courier c "
            + "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.deliveredTimestamp IS NOT NULL "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY c.id")
    List<FulfillmentTimeProjection> findShipToDeliverTimeByCourier(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r.id AS regionId, "
            + "r.city AS city, "
            + "r.country AS country, "
            + "COUNT(o) AS orderCount, "
            + "AVG(o.totalAmount) AS averageOrderValue "
            + "FROM Order o "
            + "JOIN o.region r "
            + "WHERE o.finalStatusTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY r.id, r.city, r.country "
            + "ORDER BY orderCount DESC, averageOrderValue DESC")
    List<OrderRegionProjection> findOrdersByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o.status AS status, "
            + "COUNT(o) AS count "
            + "FROM Order o "
            + "WHERE o.finalStatusTimestamp BETWEEN :startDate AND :endDate "
            + "GROUP BY o.status "
            + "ORDER BY count DESC")
    List<OrderStatusProjection> findOrderStatusCounts(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o.failureReason AS reason, "
            + "COUNT(o) AS count "
            + "FROM Order o "
            + "WHERE o.finalStatusTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.status IN ('DELIVERY_FAILED', 'CANCELLED') "
            + "GROUP BY o.failureReason "
            + "ORDER BY count DESC")
    List<OrderFailureReasonsProjection> findFailureReasons(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT (SUM(CASE WHEN o.status = 'DELIVERY_FAILED' "
            + "THEN 1 ELSE 0 END) * 1.0 "
            + "/ COUNT(o)) AS failureRate "
            + "FROM Order o "
            + "WHERE o.finalStatusTimestamp BETWEEN :startDate AND :endDate")
    OrderFailureRateProjection calculateFailureRate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT period AS period, "
            + "SUM(rev) AS totalRevenue "
            + "FROM ( "
            + "     SELECT CASE "
            + "     WHEN :reportPeriod = 'DAILY' "
            + "     THEN CAST(o.orderPlacedTimestamp AS date) "
            + "     WHEN :reportPeriod = 'WEEKLY' "
            + "     THEN FUNCTION('DATE_TRUNC','week',o.orderPlacedTimestamp) "
            + "     WHEN :reportPeriod = 'MONTHLY' "
            + "     THEN FUNCTION('DATE_TRUNC','month',o.orderPlacedTimestamp) "
            + "     END AS period, "
            + "     o.totalAmount AS rev "
            + "FROM Order o "
            + "WHERE o.finalStatusTimestamp >= :startDate "
            + "AND o.finalStatusTimestamp <  :endDate "
            + "AND o.status = 'DELIVERED' "
            + ") x "
            + "GROUP BY period "
            + "ORDER BY totalRevenue DESC")
    List<RevenueSummaryProjection> findRevenueSummaryByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("reportPeriod") String reportPeriod);

    @Query("SELECT p.category AS category, "
            + "SUM(oi.quantity * oi.pricePerUnit) AS totalRevenue "
            + "FROM OrderItem oi "
            + "JOIN oi.order o "
            + "JOIN oi.product p "
            + "WHERE o.finalStatusTimestamp >= :startDate "
            + "AND o.finalStatusTimestamp <  :endDate "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY p.category "
            + "ORDER BY totalRevenue DESC")
    List<RevenueByCategoryProjection> findRevenueByCategory(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
