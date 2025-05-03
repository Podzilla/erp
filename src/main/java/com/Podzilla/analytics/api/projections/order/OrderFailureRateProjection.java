package com.Podzilla.analytics.api.projections.order;

import java.math.BigDecimal;

public interface OrderFailureRateProjection {
    BigDecimal getFailureRate();
}
