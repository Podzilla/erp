package com.Podzilla.analytics.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.courier.CourierPerformanceProjection;
import com.Podzilla.analytics.models.Courier;
import java.util.UUID;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    @Query(value = "SELECT c.id AS courierId, "
            + "c.name AS courierName, "
            + "COUNT(o.id) AS deliveryCount, "
            + "SUM(CASE WHEN o.status = 'COMPLETED' THEN 1 ELSE 0 END) "
            + "AS completedCount, "
            + "AVG(CASE WHEN o.status = 'COMPLETED' THEN o.courier_rating "
            + "ELSE NULL END) AS averageRating "
            + "FROM couriers c "
            + "LEFT JOIN orders o "
            + "ON c.id = o.courier_id "
            + "AND o.final_status_timestamp BETWEEN :startDate AND :endDate "
            + "GROUP BY c.id, c.name "
            + "ORDER BY courierId", nativeQuery = true)
    List<CourierPerformanceProjection> findCourierPerformanceBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
