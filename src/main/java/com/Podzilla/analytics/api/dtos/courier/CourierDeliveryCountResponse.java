package com.Podzilla.analytics.api.dtos.courier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDeliveryCountResponse {

    @Schema(description = "ID of the courier", example = "101")
    private UUID courierId;

    @Schema(description = "Full name of the courier", example = "Jane Smith")
    private String courierName;

    @Schema(
        description = "Total number of deliveries (successful + failed)",
        example = "134"
    )
    private Long deliveryCount;
}
