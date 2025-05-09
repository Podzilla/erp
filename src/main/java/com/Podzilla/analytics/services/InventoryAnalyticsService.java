package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.inventory.InventoryValueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.inventory.LowStockProductResponse;
import com.Podzilla.analytics.repositories.InventorySnapshotRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryAnalyticsService {
    private final InventorySnapshotRepository inventorySnapshotRepository;

    public List<InventoryValueByCategoryResponse> getInventoryValueByCategory() {
        List<InventoryValueByCategoryResponse> inventoryValueByCategory = inventorySnapshotRepository
                .getInventoryValueByCategory()
                .stream()
                .map(row -> InventoryValueByCategoryResponse.builder()
                        .category(row.getCategory())
                        .totalStockValue(row.getTotalStockValue())
                        .build())
                .toList();
        return inventoryValueByCategory;
    }

    public Page<LowStockProductResponse> getLowStockProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LowStockProductResponse> lowStockProducts = inventorySnapshotRepository.getLowStockProducts(pageRequest)
                .map(row -> LowStockProductResponse.builder()
                        .productId(row.getProductId())
                        .productName(row.getProductName())
                        .currentQuantity(row.getCurrentQuantity())
                        .threshold(row.getThreshold())
                        .build());
        return lowStockProducts;
    }
}
