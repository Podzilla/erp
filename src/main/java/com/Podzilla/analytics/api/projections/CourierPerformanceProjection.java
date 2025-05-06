package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;

/**
 * Projection interface for courier performance statistics.
 */
public interface CourierPerformanceProjection {

    /**
     * Gets the courier ID.
     *
     * @return the courier ID
     */
    Long getCourierId();

    /**
     * Gets the full name of the courier.
     *
     * @return the courier name
     */
    String getCourierName();

    /**
     * Gets the total number of deliveries.
     *
     * @return the delivery count
     */
    Long getDeliveryCount();

    /**
     * Gets the total number of completed deliveries.
     *
     * @return the completed delivery count
     */
    Long getCompletedCount();

    /**
     * Gets the average customer rating for the courier.
     *
     * @return the average rating
     */
    BigDecimal getAverageRating();
}
