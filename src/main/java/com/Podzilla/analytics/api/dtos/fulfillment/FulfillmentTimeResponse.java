package com.Podzilla.analytics.api.dtos.fulfillment;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FulfillmentTimeResponse {

    @Schema(
            description = "Group by value (overall or by ID)",
            example = "OVERALL")
    private String groupByValue;

    @Schema(
            description = "Average duration in hours",
            example = "48.5")
    private BigDecimal averageDuration;
}
