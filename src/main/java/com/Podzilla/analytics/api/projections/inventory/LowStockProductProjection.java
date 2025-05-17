package com.Podzilla.analytics.api.projections.inventory;


public interface LowStockProductProjection {
    Long getProductId();

    String getProductName();

    Long getCurrentQuantity();

    Long getThreshold();
}
