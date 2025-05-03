package com.Podzilla.analytics.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.models.InventorySnapshot;

import java.util.List;

@Repository
public interface InventorySnapshotRepository extends JpaRepository<InventorySnapshot, Long> {

    @Query("SELECT p.category as category, SUM(s.quantity * p.cost) as totalStockValue " +
            "FROM InventorySnapshot s " +
            "JOIN s.product p " +
            "WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM InventorySnapshot s2 WHERE s2.product = s.product) " +
            "GROUP BY p.category")
    List<Object[]> getInventoryValueByCategory();

    @Query("SELECT p.id as productId, p.name as productName, s.quantity as currentQuantity, p.lowStockThreshold as threshold "
            +
            "FROM InventorySnapshot s " +
            "JOIN s.product p " +
            "WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM InventorySnapshot s2 WHERE s2.product = s.product) " +
            "AND s.quantity <= p.lowStockThreshold")
    Page<Object[]> getLowStockProducts(Pageable pageable);
}