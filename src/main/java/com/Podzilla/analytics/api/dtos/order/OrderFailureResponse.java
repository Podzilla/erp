package com.Podzilla.analytics.api.dtos.order;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailureResponse {

    @Schema(description = "Percentage of orders that failed",
        example = "0.25")
    private BigDecimal failureRate;

    @Schema(description = "List of reasons for order failures",
        example = "[{\"reason\": \"Out of stock\", \"count\": 10},"
        + "{\"reason\": \"Payment failure\", \"count\": 5}]")
    private List<OrderFailureReasonsResponse> reasons;
}
