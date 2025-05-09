package com.Podzilla.analytics.api.projections;


public interface LowStockProductProjection {
    Long getProductId();

    String getProductName();

    Long getCurrentQuantity();

    Long getThreshold();
}
