package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.inventory.InventoryValueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.inventory.LowStockProductResponse;
import com.Podzilla.analytics.repositories.ProductSnapshotRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryAnalyticsService {
    private final ProductSnapshotRepository inventoryRepo;

public List<InventoryValueByCategoryResponse> getInventoryValueByCategory() {
List<InventoryValueByCategoryResponse> invVByCy = inventoryRepo
.getInventoryValueByCategory()
.stream()
.map(row -> InventoryValueByCategoryResponse.builder()
.category(row.getCategory())
.totalStockValue(row.getTotalStockValue())
.build())
                .toList();
        return invVByCy;
    }

public Page<LowStockProductResponse> getLowStockProducts(final int page,
 final int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
Page<LowStockProductResponse> lowStockPro =
 inventoryRepo.getLowStockProducts(pageRequest)
                .map(row -> LowStockProductResponse.builder()
                        .productId(row.getProductId())
                        .productName(row.getProductName())
                        .currentQuantity(row.getCurrentQuantity())
                        .threshold(row.getThreshold())
                        .build());
        return lowStockPro;
    }
}
