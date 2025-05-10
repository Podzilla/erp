package com.Podzilla.analytics.api.dtos.inventory;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryValueByCategoryResponse {
    private String category;
    private BigDecimal totalStockValue;
}
