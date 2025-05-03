package com.Podzilla.analytics.api.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailureReasonsDTO {
    private String reason;
    private Long count;
}
