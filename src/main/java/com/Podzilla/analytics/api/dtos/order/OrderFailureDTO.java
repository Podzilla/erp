package com.Podzilla.analytics.api.dtos.order;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailureDTO {
    private BigDecimal failureRate;
    private List<OrderFailureReasonsDTO> reasons;
}
