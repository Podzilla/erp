package com.Podzilla.analytics.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.api.projections.InventoryValueByCategoryProjection;
import com.Podzilla.analytics.api.projections.LowStockProductProjection;
import com.Podzilla.analytics.models.InventorySnapshot;

@Repository
public interface InventorySnapshotRepository extends JpaRepository<InventorySnapshot, Long> {

    @Query(value = "SELECT p.category as category, SUM(s.quantity * p.cost) as totalStockValue " +
            "FROM inventory_snapshots s " +
            "JOIN products p ON s.product_id = p.id " +
            "WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM inventory_snapshots s2 WHERE s2.product_id = s.product_id) " +
            "GROUP BY p.category", nativeQuery = true)
    List<InventoryValueByCategoryProjection> getInventoryValueByCategory();

    @Query(value = "SELECT p.id as productId, p.name as productName, s.quantity as currentQuantity, p.low_stock_threshold as threshold "
            +
            "FROM inventory_snapshots s " +
            "JOIN products p ON s.product_id = p.id " +
            "WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM inventory_snapshots s2 WHERE s2.product_id = s.product_id) " +
            "AND s.quantity <= p.low_stock_threshold", nativeQuery = true)
    Page<LowStockProductProjection> getLowStockProducts(Pageable pageable);
}