package com.Podzilla.analytics.api.projections.revenue;


import java.math.BigDecimal;

public interface RevenueByCategoryProjection {
    String getCategory();
    BigDecimal getTotalRevenue();
}
