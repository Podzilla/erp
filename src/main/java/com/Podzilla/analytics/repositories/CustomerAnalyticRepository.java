package com.Podzilla.analytics.repositories;

// Place in com.podzilla.erp.analytics.repository

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.CustomerAnalytic;

@Repository
public interface CustomerAnalyticRepository extends JpaRepository<CustomerAnalytic, String> {
    // Automatic CRUD methods provided

    // Example custom query:
    // List<CustomerAnalytic> findByCustomerIdAndTimestampBetween(String customerId, Instant start, Instant end);
}