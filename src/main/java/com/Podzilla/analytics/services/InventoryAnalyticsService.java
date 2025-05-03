package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.InventoryValueByCategoryDTO;
import com.Podzilla.analytics.api.dtos.LowStockProductDTO;
import com.Podzilla.analytics.repositories.InventorySnapshotRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryAnalyticsService {
    private final InventorySnapshotRepository inventorySnapshotRepository;

    public List<InventoryValueByCategoryDTO> getInventoryValueByCategory() {
        List<InventoryValueByCategoryDTO> inventoryValueByCategory = inventorySnapshotRepository
                .getInventoryValueByCategory()
                .stream()
                .map(row -> InventoryValueByCategoryDTO.builder()
                        .category(row.getCategory())
                        .totalStockValue(row.getTotalStockValue())
                        .build())
                .toList();
        return inventoryValueByCategory;
    }

    public Page<LowStockProductDTO> getLowStockProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LowStockProductDTO> lowStockProducts = inventorySnapshotRepository.getLowStockProducts(pageRequest)
                .map(row -> LowStockProductDTO.builder()
                        .productId(row.getProductId())
                        .productName(row.getProductName())
                        .currentQuantity(row.getCurrentQuantity())
                        .threshold(row.getThreshold())
                        .build());
        return lowStockProducts;
    }
}
