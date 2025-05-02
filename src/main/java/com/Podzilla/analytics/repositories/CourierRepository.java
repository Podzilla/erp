package com.Podzilla.analytics.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Podzilla.analytics.api.DTOs.CourierAverageRatingDTO;
import com.Podzilla.analytics.api.DTOs.CourierDeliveryCountDTO;
import com.Podzilla.analytics.api.DTOs.CourierSuccessRateDTO;
import com.Podzilla.analytics.models.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query(value = """
            SELECT c.id AS courierId,
                   c.name AS courierName,
                   COUNT(o.id) AS deliveriesCount
            FROM couriers c
            LEFT JOIN orders o
                ON c.id = o.courier_id
                AND o.delivered_timestamp BETWEEN :startDate AND :endDate
            GROUP BY c.id, c.name
            ORDER BY deliveriesCount DESC
            """, nativeQuery = true)
    List<CourierDeliveryCountDTO> getCourierDeliveryCounts(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query(value = """
            SELECT c.id AS courierId,
                   c.name AS courierName,
                   CASE WHEN COUNT(o.id) = 0 THEN 0
                        ELSE CAST(SUM(CASE WHEN o.status = 'COMPLETED' THEN 1 ELSE 0 END) AS FLOAT) / COUNT(o.id)
                   END AS successRate
            FROM couriers c
            LEFT JOIN orders o
                   ON c.id = o.courier_id
                   AND o.final_status_timestamp BETWEEN :startDate AND :endDate
            GROUP BY c.id, c.name
            ORDER BY successRate DESC
            """, nativeQuery = true)
    List<CourierSuccessRateDTO> getCourierSuccessRate(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query(value = """
            SELECT c.id AS courierId,
                   c.name AS courierName,
                   AVG(o.courier_rating) AS averageRating
            FROM couriers c
            LEFT JOIN orders o
                ON c.id = o.courier_id
                AND o.final_status_timestamp BETWEEN :startDate AND :endDate
            GROUP BY c.id, c.name
            ORDER BY averageRating DESC
            """, nativeQuery = true)
    List<CourierAverageRatingDTO> getCourierAverageRating(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
