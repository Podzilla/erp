package com.Podzilla.analytics.api.projections;


import java.math.BigDecimal;

public interface RevenueByCategoryProjection {
    String getCategory();
    BigDecimal getTotalRevenue();
}
