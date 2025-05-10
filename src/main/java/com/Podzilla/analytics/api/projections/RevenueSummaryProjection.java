package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RevenueSummaryProjection {
    LocalDate getPeriod();         // The grouped period: daily/week/month
    BigDecimal getTotalRevenue(); // The sum of revenue for that period
}