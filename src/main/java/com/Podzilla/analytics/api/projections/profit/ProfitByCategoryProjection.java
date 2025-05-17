package com.Podzilla.analytics.api.projections.profit;

import java.math.BigDecimal;

/**
 * Projection interface for profit by category query results
 */
public interface ProfitByCategoryProjection {
    String getCategory();
    BigDecimal getTotalRevenue();
    BigDecimal getTotalCost();
}
