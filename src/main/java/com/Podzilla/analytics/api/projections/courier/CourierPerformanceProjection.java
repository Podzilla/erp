package com.Podzilla.analytics.api.projections.courier;

import java.math.BigDecimal;
import java.util.UUID;

public interface CourierPerformanceProjection {
    UUID getCourierId();

    String getCourierName();

    Long getDeliveryCount();

    Long getCompletedCount();

    BigDecimal getAverageRating();
}
