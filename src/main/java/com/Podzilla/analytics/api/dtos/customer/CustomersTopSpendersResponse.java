package com.Podzilla.analytics.api.dtos.customer;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersTopSpendersResponse {
    private UUID customerId;
    private String customerName;
    private BigDecimal totalSpending;
}
