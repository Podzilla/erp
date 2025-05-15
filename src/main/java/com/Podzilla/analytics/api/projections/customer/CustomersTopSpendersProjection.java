package com.Podzilla.analytics.api.projections.customer;

import java.math.BigDecimal;

public interface CustomersTopSpendersProjection {
    Long getCustomerId();

    String getCustomerName();

    BigDecimal getTotalSpending();
}
