package com.Podzilla.analytics.api.projections.product;

import java.math.BigDecimal;
import java.util.UUID;

public interface TopSellingProductProjection {
    UUID getId();
    String getName();
    String getCategory();
    BigDecimal getTotalRevenue();
    Long getTotalUnits();
}
