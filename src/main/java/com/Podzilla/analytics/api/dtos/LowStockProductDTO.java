package com.Podzilla.analytics.api.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockProductDTO {
    private Long productId;
    private String productName;
    private Long currentQuantity;
    private Long threshold;
}
