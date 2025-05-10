package com.Podzilla.analytics.api.projections.fulfillment;

public interface FulfillmentTimeProjection {
    String getGroupByValue();
    Double getAverageDuration();
}
