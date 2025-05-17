package com.Podzilla.analytics.api.projections.inventory;

import java.math.BigDecimal;

public interface InventoryValueByCategoryProjection {
    String getCategory();

    BigDecimal getTotalStockValue();
}
