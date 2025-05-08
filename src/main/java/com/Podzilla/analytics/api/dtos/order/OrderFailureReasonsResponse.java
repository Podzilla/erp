package com.Podzilla.analytics.api.dtos.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFailureReasonsResponse {

    @Schema(description = "Reason for order failure",
        example = "Payment declined")
    private String reason;

    @Schema(description = "Count of orders with this failure reason",
        example = "150")
    private Long count;
}
