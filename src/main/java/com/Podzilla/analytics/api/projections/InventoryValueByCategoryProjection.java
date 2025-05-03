package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;

public interface InventoryValueByCategoryProjection {
    String getCategory();

    BigDecimal getTotalStockValue();
}
