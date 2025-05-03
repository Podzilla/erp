package com.Podzilla.analytics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}