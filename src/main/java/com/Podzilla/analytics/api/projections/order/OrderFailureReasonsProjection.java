package com.Podzilla.analytics.api.projections.order;

public interface OrderFailureReasonsProjection {
    String getReason();
    Long getCount();
}
