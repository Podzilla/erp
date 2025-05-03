package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;

public interface CourierPerformanceProjection {
    Long getCourierId();

    String getCourierName();

    Long getDeliveryCount();

    Long getCompletedCount();

    BigDecimal getAverageRating();
}
