package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryValueByCategoryDTO {
    private String category;
    private BigDecimal totalStockValue;
}
