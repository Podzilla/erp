package com.Podzilla.analytics.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.projections.courier.CourierPerformanceProjection;
import com.Podzilla.analytics.models.Courier;

public interface CourierRepository extends JpaRepository<Courier, UUID> {

    @Query("SELECT c.id AS courierId, "
            + "c.name AS courierName, "
            + "COUNT(o.id) AS deliveryCount, "
            + "SUM(CASE WHEN o.status = 'COMPLETED' THEN 1 ELSE 0 END) "
            + "AS completedCount, "
            + "AVG(CASE WHEN o.status = 'COMPLETED' THEN o.courierRating "
            + "ELSE NULL END) AS averageRating "
            + "FROM Courier c "
            + "LEFT JOIN Order o "
            + "ON c.id = o.courier.id "
            + "AND o.finalStatusTimestamp BETWEEN :startDate AND :endDate "
            + "GROUP BY c.id, c.name "
            + "ORDER BY c.id")
    List<CourierPerformanceProjection> findCourierPerformanceBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
