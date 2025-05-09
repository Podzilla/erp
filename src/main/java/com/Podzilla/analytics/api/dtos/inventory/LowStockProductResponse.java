package com.Podzilla.analytics.api.dtos.inventory;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockProductResponse {
    private Long productId;
    private String productName;
    private Long currentQuantity;
    private Long threshold;
}
