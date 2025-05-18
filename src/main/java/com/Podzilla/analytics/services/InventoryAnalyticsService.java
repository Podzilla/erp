package com.Podzilla.analytics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.api.dtos.inventory.InventoryValueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.inventory.LowStockProductResponse;
import com.Podzilla.analytics.repositories.ProductSnapshotRepository;
import com.Podzilla.analytics.repositories.ProductRepository;
import com.Podzilla.analytics.models.Product;
import com.Podzilla.analytics.models.ProductSnapshot;
import com.Podzilla.analytics.util.StringToUUIDParser;
import com.Podzilla.analytics.util.DatetimeFormatter;

import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryAnalyticsService {
    private final ProductSnapshotRepository inventoryRepo;
    private final ProductRepository productRepository;

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

    public void saveInventorySnapshot(
            final String productId,
            final Integer quantity,
            final Instant timestamp
    ) {
        UUID productUUID = StringToUUIDParser.parseStringToUUID(productId);
        Product product = productRepository.findById(productUUID)
                .orElseThrow(
                    () -> new IllegalArgumentException("Product not found")
                );
        LocalDateTime snapshotTimestamp = DatetimeFormatter
                .convertIntsantToDateTime(timestamp);
        ProductSnapshot inventorySnapshot = ProductSnapshot.builder()
                .product(product)
                .quantity(quantity)
                .timestamp(snapshotTimestamp)
                .build();

        inventoryRepo.save(inventorySnapshot);
    }
}
