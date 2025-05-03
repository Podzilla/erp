package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.repositories.InventorySnapshotRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryAnalyticsService {
    private final InventorySnapshotRepository inventorySnapshotRepository;

    public InventoryAnalyticsService(InventorySnapshotRepository inventorySnapshotRepository) {
        this.inventorySnapshotRepository = inventorySnapshotRepository;
    }

    public List<Map<String, Object>> getInventoryValueByCategory() {
        return inventorySnapshotRepository.getInventoryValueByCategory().stream()
                .map(row -> Map.of(
                        "category", row[0],
                        "totalStockValue", row[1]))
                .collect(Collectors.toList());
    }

    public Page<Map<String, Object>> getLowStockProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return inventorySnapshotRepository.getLowStockProducts(pageRequest)
                .map(row -> Map.of(
                        "productId", row[0],
                        "productName", row[1],
                        "currentQuantity", row[2],
                        "threshold", row[3]));
    }
}
