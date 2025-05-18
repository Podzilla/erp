package com.Podzilla.analytics.api.dtos.order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRegionResponse {

    @Schema(description = "Region ID",
        example = "4731e9e0-c627-43f9-808a-7e8637abb912")
    private UUID regionId;

    @Schema(description = "city name", example = "Metropolis")
    private String city;

    @Schema(description = "country name", example = "USA")
    private String country;

    @Schema(description = "number of orders in the region",
        example = "100")
    private Long orderCount;

    @Schema(description = "average revenue generated from orders in the region",
        example = "10000.00")
    private BigDecimal averageOrderValue;
}
