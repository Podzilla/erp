package com.Podzilla.analytics.api.projections.customer;

import java.math.BigDecimal;
import java.util.UUID;

public interface CustomersTopSpendersProjection {
    UUID getCustomerId();

    String getCustomerName();

    BigDecimal getTotalSpending();
}
