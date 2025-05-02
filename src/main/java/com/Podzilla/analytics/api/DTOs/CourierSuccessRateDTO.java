package com.Podzilla.analytics.api.DTOs;

import java.math.BigDecimal;

public interface CourierSuccessRateDTO {
    Long getCourierId();

    String getCourierName();

    BigDecimal getSuccessRate();
}
