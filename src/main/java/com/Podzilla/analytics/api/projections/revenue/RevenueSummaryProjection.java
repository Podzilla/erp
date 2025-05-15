package com.Podzilla.analytics.api.projections.revenue;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RevenueSummaryProjection {
    LocalDate getPeriod();
    BigDecimal getTotalRevenue();
}
