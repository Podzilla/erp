package com.Podzilla.analytics.repositories;

// Place in com.podzilla.erp.analytics.repository

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.OrderAnalytics;

@Repository
public interface OrderAnalyticRepository  extends JpaRepository<OrderAnalytics, String> {
    // Automatic CRUD methods provided

    // Example custom queries:
    // List<Order> findByCustomerIdAndTimestampBetween(String customerId, Instant start, Instant end);
    // List<Order> findByStatus(String status);
    // Optional<Order> findByOrderId(String orderId); // If originalOrderId is unique and you need lookup by it
}