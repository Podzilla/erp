package com.Podzilla.analytics.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Podzilla.analytics.api.projections.inventory.InventoryValueByCategoryProjection;
import com.Podzilla.analytics.api.projections.inventory.LowStockProductProjection;
import com.Podzilla.analytics.models.ProductSnapshot;

@Repository
public interface ProductSnapshotRepository
        extends JpaRepository<ProductSnapshot, Long> {

    @Query("SELECT p.category AS category, "
            + "SUM(s.quantity * p.cost) AS totalStockValue "
            + "FROM ProductSnapshot s "
            + "JOIN s.product p "
            + "WHERE s.timestamp = (SELECT MAX(s2.timestamp) "
            + "                     FROM ProductSnapshot s2 "
            + "                     WHERE s2.product.id = s.product.id) "
            + "GROUP BY p.category")
    List<InventoryValueByCategoryProjection> getInventoryValueByCategory();

    @Query("SELECT p.id AS productId, "
            + "p.name AS productName, "
            + "s.quantity AS currentQuantity, "
            + "p.lowStockThreshold AS threshold "
            + "FROM ProductSnapshot s "
            + "JOIN s.product p "
            + "WHERE s.timestamp = (SELECT MAX(s2.timestamp) "
            + "                     FROM ProductSnapshot s2 "
            + "                     WHERE s2.product.id = s.product.id) "
            + "AND s.quantity <= p.lowStockThreshold")
    Page<LowStockProductProjection> getLowStockProducts(Pageable pageable);
}
