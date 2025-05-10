package com.Podzilla.analytics.api.dtos.order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRegionResponse {

    @Schema(description = "Region ID", example = "12345")
    private Long regionId;

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
