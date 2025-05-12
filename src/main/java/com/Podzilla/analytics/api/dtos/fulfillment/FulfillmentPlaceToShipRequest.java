package com.Podzilla.analytics.api.dtos.fulfillment;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidDateRange
public class FulfillmentPlaceToShipRequest {

    /**
     * Enum for grouping options in place-to-ship analytics.
     */
    public enum PlaceToShipGroupBy {
        REGION, OVERALL
    }

    @NotNull(message = "startDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date of the range (inclusive)",
     example = "2024-01-01", required = true)
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date of the range (inclusive)",
     example = "2024-01-31", required = true)
    private LocalDate endDate;

    @NotNull(message = "groupBy is required")
    @Schema(description = "How to group the results (OVERALL, REGION, COURIER "
            + "depending on endpoint)", example = "OVERALL", required = true)
    private PlaceToShipGroupBy groupBy;

}
