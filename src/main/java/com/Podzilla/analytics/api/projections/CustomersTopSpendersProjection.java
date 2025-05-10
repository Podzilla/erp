package com.Podzilla.analytics.api.projections;

import java.math.BigDecimal;

public interface CustomersTopSpendersProjection {
    Long getCustomerId();

    String getCustomerName();

    BigDecimal getTotalSpending();
}
