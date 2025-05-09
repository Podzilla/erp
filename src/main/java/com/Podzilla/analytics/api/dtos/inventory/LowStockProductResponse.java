package com.Podzilla.analytics.api.dtos.inventory;

import lombok.*;

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
