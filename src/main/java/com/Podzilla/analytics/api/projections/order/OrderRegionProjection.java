package com.Podzilla.analytics.api.projections.order;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrderRegionProjection {
    UUID getRegionId();
    String getCity();
    String getCountry();
    Long getOrderCount();
    BigDecimal getAverageOrderValue();
}
