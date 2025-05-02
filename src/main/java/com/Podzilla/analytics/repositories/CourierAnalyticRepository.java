package com.Podzilla.analytics.repositories;

// Place in com.podzilla.erp.analytics.repository

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.CourierAnalytic;

@Repository // Optional annotation for interfaces, but good practice
public interface CourierAnalyticRepository extends JpaRepository<CourierAnalytic, String> {
    // Spring Data JPA automatically provides:
    // save(CourierAnalytic entity)
    // findById(String id)
    // findAll()
    // deleteById(String id)
    // etc.

    // Add custom query methods here if needed later, e.g.,
    // List<CourierAnalytic> findByCourierIdAndDispatchTimestampBetween(String courierId, Instant start, Instant end);
}