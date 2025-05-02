package com.Podzilla.analytics.api.DTOs;

import java.math.BigDecimal;

public interface CourierAverageRatingDTO {
    Long getCourierId();

    String getCourierName();

    BigDecimal getAverageRating();
}
