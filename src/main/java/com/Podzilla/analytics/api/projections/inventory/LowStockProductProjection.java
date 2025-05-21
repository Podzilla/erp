package com.Podzilla.analytics.api.projections.inventory;

import java.util.UUID;
public interface LowStockProductProjection {
    UUID getProductId();

    String getProductName();

    Long getCurrentQuantity();

    Long getThreshold();
}
