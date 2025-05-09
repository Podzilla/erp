package com.Podzilla.analytics.api.dtos.inventory;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryValueByCategoryResponse {
    private String category;
    private BigDecimal totalStockValue;
}
