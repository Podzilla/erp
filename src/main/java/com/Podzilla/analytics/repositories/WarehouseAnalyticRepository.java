package com.Podzilla.analytics.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.WarehouseAnalytic;

@Repository
public interface WarehouseAnalyticRepository extends JpaRepository<WarehouseAnalytic, String> {
    // Automatic CRUD methods provided

    // Example custom queries:
    // List<WarehouseAnalytic> findByProductIdAndTimestampBetween(String productId, Instant start, Instant end);
    // List<WarehouseAnalytic> findByIsLowTrue(); // Finds entities where isLow is true
}