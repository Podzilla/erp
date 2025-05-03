package com.Podzilla.analytics.api.dtos;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomersTopSpendersDTO {
    private Long customerId;
    private String customerName;
    private BigDecimal totalSpending;
}
