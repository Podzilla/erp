package com.Podzilla.analytics.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.api.projections.customer.CustomersTopSpendersProjection;
import com.Podzilla.analytics.models.Customer;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.id AS customerId, c.name AS customerName, "
            + "COALESCE(SUM(o.totalAmount), 0) AS totalSpending "
            + "FROM Customer c "
            + "LEFT JOIN c.orders o "
            + "WITH o.finalStatusTimestamp BETWEEN :startDate AND :endDate "
            + "AND o.status = 'DELIVERED' "
            + "GROUP BY c.id, c.name "
            + "ORDER BY totalSpending DESC")
    Page<CustomersTopSpendersProjection> findTopSpenders(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
