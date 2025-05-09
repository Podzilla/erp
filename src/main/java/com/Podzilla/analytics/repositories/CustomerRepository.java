package com.Podzilla.analytics.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.api.projections.CustomersTopSpendersProjection;
import com.Podzilla.analytics.models.Customer;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

        @Query(value = "SELECT c.id as customerId, c.name as customerName, "
                        + "SUM(o.total_amount) as totalSpending "
                        + "FROM customers c "
                        + "JOIN orders o ON c.id = o.customer_id "
                        + "WHERE o.order_placed_timestamp "
                        + "BETWEEN :startDate AND :endDate "
                        + "GROUP BY c.id, c.name "
                        + "ORDER BY totalSpending DESC", nativeQuery = true)
        Page<CustomersTopSpendersProjection> findTopSpenders(
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate,
                        Pageable pageable);
}
