package com.Podzilla.analytics.api.dtos.customer;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersTopSpendersResponse {
    private Long customerId;
    private String customerName;
    private BigDecimal totalSpending;
}
