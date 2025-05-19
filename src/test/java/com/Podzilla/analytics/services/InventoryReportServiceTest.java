package com.Podzilla.analytics.services;

import com.Podzilla.analytics.api.dtos.inventory.InventoryValueByCategoryResponse;
import com.Podzilla.analytics.api.dtos.inventory.LowStockProductResponse;
import com.Podzilla.analytics.api.projections.inventory.InventoryValueByCategoryProjection;
import com.Podzilla.analytics.api.projections.inventory.LowStockProductProjection;
import com.Podzilla.analytics.repositories.ProductSnapshotRepository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryReportServiceTest {

    @Mock
    private ProductSnapshotRepository inventoryRepo;

    @InjectMocks
    private InventoryAnalyticsService inventoryAnalyticsService;

    private InventoryValueByCategoryProjection createMockInventoryProjection(
            String category, BigDecimal totalStockValue) {
        InventoryValueByCategoryProjection mockProjection = Mockito.mock(InventoryValueByCategoryProjection.class);
        Mockito.lenient().when(mockProjection.getCategory()).thenReturn(category);
        Mockito.lenient().when(mockProjection.getTotalStockValue()).thenReturn(totalStockValue);
        return mockProjection;
    }

    private LowStockProductProjection createMockLowStockProjection(
            UUID productId, String productName, Long currentQuantity, Long threshold) {
        LowStockProductProjection mockProjection = Mockito.mock(LowStockProductProjection.class);
        Mockito.lenient().when(mockProjection.getProductId()).thenReturn(productId);
        Mockito.lenient().when(mockProjection.getProductName()).thenReturn(productName);
        Mockito.lenient().when(mockProjection.getCurrentQuantity()).thenReturn(currentQuantity);
        Mockito.lenient().when(mockProjection.getThreshold()).thenReturn(threshold);
        return mockProjection;
    }

    @Test
    void getInventoryValueByCategory_shouldReturnCorrectValuesForMultipleCategories() {
        // Arrange
        InventoryValueByCategoryProjection electronicsData = createMockInventoryProjection(
                "Electronics", new BigDecimal("50000.00"));
        InventoryValueByCategoryProjection clothingData = createMockInventoryProjection(
                "Clothing", new BigDecimal("20000.00"));

        when(inventoryRepo.getInventoryValueByCategory())
                .thenReturn(Arrays.asList(electronicsData, clothingData));

        // Act
        List<InventoryValueByCategoryResponse> result = inventoryAnalyticsService
                .getInventoryValueByCategory();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        InventoryValueByCategoryResponse electronicsResponse = result.stream()
                .filter(r -> r.getCategory().equals("Electronics"))
                .findFirst().orElse(null);
        assertNotNull(electronicsResponse);
        assertEquals(new BigDecimal("50000.00"), electronicsResponse.getTotalStockValue());

        InventoryValueByCategoryResponse clothingResponse = result.stream()
                .filter(r -> r.getCategory().equals("Clothing"))
                .findFirst().orElse(null);
        assertNotNull(clothingResponse);
        assertEquals(new BigDecimal("20000.00"), clothingResponse.getTotalStockValue());

        Mockito.verify(inventoryRepo).getInventoryValueByCategory();
    }

    @Test
    void getInventoryValueByCategory_shouldReturnEmptyListWhenNoData() {
        // Arrange
        when(inventoryRepo.getInventoryValueByCategory())
                .thenReturn(Collections.emptyList());

        // Act
        List<InventoryValueByCategoryResponse> result = inventoryAnalyticsService
                .getInventoryValueByCategory();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(inventoryRepo).getInventoryValueByCategory();
    }

    @Test
    void getLowStockProducts_shouldReturnCorrectProducts() {
        // Arrange
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        LowStockProductProjection product1Data = createMockLowStockProjection(
                productId1, "Laptop", 5L, 10L);
        LowStockProductProjection product2Data = createMockLowStockProjection(
                productId2, "Mouse", 2L, 5L);

        Page<LowStockProductProjection> mockPage = new PageImpl<>(
                Arrays.asList(product1Data, product2Data));

        when(inventoryRepo.getLowStockProducts(any(PageRequest.class)))
                .thenReturn(mockPage);

        // Act
        Page<LowStockProductResponse> result = inventoryAnalyticsService
                .getLowStockProducts(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        LowStockProductResponse product1Response = result.getContent().stream()
                .filter(p -> p.getProductName().equals("Laptop"))
                .findFirst().orElse(null);
        assertNotNull(product1Response);
        assertEquals(productId1, product1Response.getProductId());
        assertEquals(5, product1Response.getCurrentQuantity());
        assertEquals(10, product1Response.getThreshold());

        LowStockProductResponse product2Response = result.getContent().stream()
                .filter(p -> p.getProductName().equals("Mouse"))
                .findFirst().orElse(null);
        assertNotNull(product2Response);
        assertEquals(productId2, product2Response.getProductId());
        assertEquals(2, product2Response.getCurrentQuantity());
        assertEquals(5, product2Response.getThreshold());

        Mockito.verify(inventoryRepo).getLowStockProducts(PageRequest.of(0, 10));
    }

    @Test
    void getLowStockProducts_shouldReturnEmptyPageWhenNoData() {
        // Arrange
        Page<LowStockProductProjection> emptyPage = new PageImpl<>(Collections.emptyList());
        when(inventoryRepo.getLowStockProducts(any(PageRequest.class)))
                .thenReturn(emptyPage);

        // Act
        Page<LowStockProductResponse> result = inventoryAnalyticsService
                .getLowStockProducts(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        Mockito.verify(inventoryRepo).getLowStockProducts(PageRequest.of(0, 10));
    }

    @Test
    void getLowStockProducts_shouldHandlePagination() {
        // Arrange
        UUID productId = UUID.randomUUID();
        LowStockProductProjection productData = createMockLowStockProjection(
                productId, "Laptop", 5L, 10L);

        Page<LowStockProductProjection> mockPage = new PageImpl<>(
                Collections.singletonList(productData));

        when(inventoryRepo.getLowStockProducts(any(PageRequest.class)))
                .thenReturn(mockPage);

        // Act
        Page<LowStockProductResponse> result = inventoryAnalyticsService
                .getLowStockProducts(0, 1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Laptop", result.getContent().get(0).getProductName());
        assertEquals(5, result.getContent().get(0).getCurrentQuantity());
        assertEquals(10, result.getContent().get(0).getThreshold());

        Mockito.verify(inventoryRepo).getLowStockProducts(PageRequest.of(0, 1));
    }
}
