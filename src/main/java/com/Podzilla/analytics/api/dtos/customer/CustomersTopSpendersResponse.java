package com.Podzilla.analytics.api.dtos.customer;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersTopSpendersResponse {
    private Long customerId;
    private String customerName;
    private BigDecimal totalSpending;
}
