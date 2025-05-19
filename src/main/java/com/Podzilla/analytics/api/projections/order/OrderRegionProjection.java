package com.Podzilla.analytics.api.projections.order;

import java.math.BigDecimal;

public interface OrderRegionProjection {
    String getCity();
    String getCountry();
    Long getOrderCount();
    BigDecimal getAverageOrderValue();
}
