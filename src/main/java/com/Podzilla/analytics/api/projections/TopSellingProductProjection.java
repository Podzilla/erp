package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;

public interface TopSellingProductProjection {
    Long getId();
    String getName();
    String getCategory();
    BigDecimal getTotalRevenue();
    Long getTotalUnits();
}
