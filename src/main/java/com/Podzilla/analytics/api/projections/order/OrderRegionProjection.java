package com.Podzilla.analytics.api.projections.order;

import java.math.BigDecimal;

public interface OrderRegionProjection {
    Long getRegionId();
    String getCity();
    String getCountry();
    Long getOrderCount();
    BigDecimal getAverageOrderValue();
}
