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
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "SELECT 'OVERALL' as groupByValue, "
             + "AVG(EXTRACT(EPOCH FROM (o.shipped_timestamp - "
            + "o.order_placed_timestamp)) / 86400) as averageDuration "
            + "FROM orders o "
            + "WHERE o.order_placed_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.shipped_timestamp IS NOT NULL", nativeQuery = true)
    FulfillmentTimeProjection findPlaceToShipTimeOverall(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT CONCAT('RegionID_', o.region_id) as groupByValue, "
            + "AVG(EXTRACT(EPOCH FROM (o.shipped_timestamp - "
            + "o.order_placed_timestamp)) / 86400) as averageDuration "
            + "FROM orders o "
            + "WHERE o.order_placed_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.shipped_timestamp IS NOT NULL "
            + "GROUP BY o.region_id", nativeQuery = true)
    List<FulfillmentTimeProjection> findPlaceToShipTimeByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT 'OVERALL' as groupByValue, "
            + "AVG(EXTRACT(EPOCH FROM (o.delivered_timestamp "
            + "- o.shipped_timestamp)) / 86400) as averageDuration "
            + "FROM orders o "
            + "WHERE o.shipped_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.delivered_timestamp IS NOT NULL "
            + "AND o.status = 'COMPLETED'", nativeQuery = true)
    FulfillmentTimeProjection findShipToDeliverTimeOverall(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT CONCAT('RegionID_', o.region_id) as groupByValue, "
            + "AVG(EXTRACT(EPOCH FROM (o.delivered_timestamp - "
            + "o.shipped_timestamp)) / 86400) as averageDuration "
            + "FROM orders o "
            + "WHERE o.shipped_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.delivered_timestamp IS NOT NULL "
            + "AND o.status = 'COMPLETED' "
            + "GROUP BY o.region_id", nativeQuery = true)
    List<FulfillmentTimeProjection> findShipToDeliverTimeByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT CONCAT('CourierID_', o.courier_id) as groupByValue, "
            + "AVG(EXTRACT(EPOCH FROM (o.delivered_timestamp - "
            + "o.shipped_timestamp)) / 86400) as averageDuration "
            + "FROM orders o "
            + "WHERE o.shipped_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.delivered_timestamp IS NOT NULL "
            + "AND o.status = 'COMPLETED' "
            + "GROUP BY o.courier_id", nativeQuery = true)
    List<FulfillmentTimeProjection> findShipToDeliverTimeByCourier(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT o.region_id as regionId, "
            + "r.city as city, "
            + "r.country as country, "
            + "count(o.id) as orderCount, "
            + "avg(o.total_amount) as averageOrderValue "
            + "FROM orders o "
            + "INNER JOIN regions r on o.region_id = r.id "
            + "WHERE o.final_status_timestamp BETWEEN :startDate AND :endDate "
            + "GROUP BY o.region_id, r.city, r.country "
            + "ORDER BY orderCount desc, averageOrderValue desc",
            nativeQuery = true)
    List<OrderRegionProjection> findOrdersByRegion(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT o.status as status, "
            + "count(o.id) as count "
            + "FROM orders o "
            + "WHERE o.final_status_timestamp BETWEEN :startDate AND :endDate "
            + "GROUP BY o.status "
            + "ORDER BY count desc",
            nativeQuery = true)
    List<OrderStatusProjection> findOrderStatusCounts(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT o.failure_reason as reason, "
            + "count(o.id) as count "
            + "FROM orders o "
            + "WHERE o.final_status_timestamp BETWEEN :startDate AND :endDate "
            + "AND o.status = 'FAILED' "
            + "GROUP BY o.failure_reason "
            + "ORDER BY count desc",
            nativeQuery = true)
    List<OrderFailureReasonsProjection> findFailureReasons(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT(SUM(CASE WHEN o.status = 'FAILED' THEN 1 ELSE 0 END)"
            + " / (count(*)*1.0) ) as failureRate "
            + "FROM orders o "
            + "WHERE o.final_status_timestamp BETWEEN :startDate"
            + " AND :endDate", nativeQuery = true)
    OrderFailureRateProjection calculateFailureRate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT "
            + "t.period, "
            + "SUM(t.total_amount) as totalRevenue "
            + "FROM ( "
            + "SELECT "
            + "CASE :reportPeriod "
            + "WHEN 'DAILY' THEN CAST(o.order_placed_timestamp AS DATE) "
            + "WHEN 'WEEKLY' THEN"
            + " date_trunc('week', o.order_placed_timestamp)::date "
            + "WHEN 'MONTHLY' THEN"
            + " date_trunc('month', o.order_placed_timestamp)::date "
            + "END as period, "
            + "o.total_amount "
            + "FROM orders o "
            + "WHERE o.order_placed_timestamp >= :startDate "
            + "AND o.order_placed_timestamp < :endDate "
            + "AND o.status IN ('COMPLETED') "
            + ") t "
            + "GROUP BY t.period "
            + "ORDER BY t.period", nativeQuery = true)
    List<RevenueSummaryProjection> findRevenueSummaryByPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("reportPeriod") String reportPeriod);

    @Query(value = "SELECT "
            + "p.category, "
            + "SUM(sli.quantity * sli.price_per_unit) as totalRevenue "
            + "FROM orders o "
            + "JOIN sales_line_items sli ON o.id = sli.order_id "
            + "JOIN products p ON sli.product_id = p.id "
            + "WHERE o.order_placed_timestamp >= :startDate "
            + "AND o.order_placed_timestamp < :endDate "
            + "AND o.status IN ('COMPLETED') "
            + "GROUP BY p.category "
            + "ORDER BY SUM(sli.quantity * sli.price_per_unit) DESC",
            nativeQuery = true)
    List<RevenueByCategoryProjection> findRevenueByCategory(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
