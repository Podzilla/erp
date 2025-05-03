package com.Podzilla.analytics.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.Customer;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c.id as customerId, c.name as customerName, SUM(o.totalAmount) as totalSpending " +
            "FROM Customer c " +
            "JOIN c.orders o " +
            "WHERE o.orderPlacedTimestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id, c.name " +
            "ORDER BY totalSpending DESC")
    Page<Object[]> findTopSpenders(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}