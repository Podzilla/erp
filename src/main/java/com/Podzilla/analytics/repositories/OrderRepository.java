package com.Podzilla.analytics.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.order.OrderFailureRateProjection;
import com.Podzilla.analytics.api.projections.order.OrderFailureReasonsProjection;
import com.Podzilla.analytics.api.projections.order.OrderRegionProjection;
import com.Podzilla.analytics.api.projections.order.OrderStatusProjection;
import com.Podzilla.analytics.models.Order;

import java.time.LocalDateTime;
import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long> {

    // --- Place to Ship Time ---

    @Query("SELECT AVG(FUNCTION('TIMESTAMPDIFF', SECOND, o.orderPlacedTimestamp, o.shippedTimestamp)) " +
           "FROM Order o " +
           "WHERE o.orderPlacedTimestamp BETWEEN :startDate AND :endDate " +
           "AND o.shippedTimestamp IS NOT NULL")
    Double findAveragePlaceToShipTimeOverall(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o.region.id, AVG(FUNCTION('TIMESTAMPDIFF', SECOND, o.orderPlacedTimestamp, o.shippedTimestamp)) " +
           "FROM Order o " +
           "WHERE o.orderPlacedTimestamp BETWEEN :startDate AND :endDate " +
           "AND o.shippedTimestamp IS NOT NULL " +
           "GROUP BY o.region.id")
    List<Object[]> findAveragePlaceToShipTimeByRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // --- Ship to Deliver Time ---

    @Query("SELECT AVG(FUNCTION('TIMESTAMPDIFF', SECOND, o.shippedTimestamp, o.deliveredTimestamp)) " +
           "FROM Order o " +
           "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate " +
           "AND o.deliveredTimestamp IS NOT NULL " +
           "AND o.status = 'COMPLETED'") // Assuming we only care about completed orders for this metric
    Double findAverageShipToDeliverTimeOverall(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o.region.id, AVG(FUNCTION('TIMESTAMPDIFF', SECOND, o.shippedTimestamp, o.deliveredTimestamp)) " +
           "FROM Order o " +
           "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate " +
           "AND o.deliveredTimestamp IS NOT NULL " +
           "AND o.status = 'COMPLETED' " +
           "GROUP BY o.region.id")
    List<Object[]> findAverageShipToDeliverTimeByRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT o.courier.id, AVG(FUNCTION('TIMESTAMPDIFF', SECOND, o.shippedTimestamp, o.deliveredTimestamp)) " +
           "FROM Order o " +
           "WHERE o.shippedTimestamp BETWEEN :startDate AND :endDate " +
           "AND o.deliveredTimestamp IS NOT NULL " +
           "AND o.status = 'COMPLETED' " +
           "GROUP BY o.courier.id")
    List<Object[]> findAverageShipToDeliverTimeByCourier(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "Select o.region_id as regionId, "
            + "r.city as city, "
            + "r.country as country, "
            + "count(o.id) as orderCount, "
            + "avg(o.total_amount) as averageOrderValue "
        + "From orders o "
        + "inner join regions r on o.region_id = r.id "
        + "where o.final_status_timestamp between :startDate and :endDate "
        + "Group by o.region_id, r.city, r.country "
        + "Order by orderCount desc, averageOrderValue desc",
    nativeQuery = true)
    List<OrderRegionProjection> findOrdersByRegion(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = "Select o.status as status, "
            + "count(o.id) as count "
        + "From orders o "
        + "where o.final_status_timestamp between :startDate and :endDate "
        + "Group by o.status "
        + "Order by count desc",
    nativeQuery = true)
    List<OrderStatusProjection> findOrderStatusCounts(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = "Select o.failure_reason as reason, "
            + "count(o.id) as count "
        + "From orders o "
        + "where o.final_status_timestamp between :startDate and :endDate "
        + "and o.status = 'FAILED' "
        + "Group by o.failure_reason "
        + "Order by count desc",
    nativeQuery = true)
    List<OrderFailureReasonsProjection> findFailureReasons(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value =
        "Select (Sum(Case when o.status = 'FAILED' then 1 else 0 end)/"
            + "(count(*)*1.0) ) as failureRate "
        + "From orders o "
        + "where o.final_status_timestamp between :startDate and :endDate",
    nativeQuery = true)
    OrderFailureRateProjection calculateFailureRate(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
