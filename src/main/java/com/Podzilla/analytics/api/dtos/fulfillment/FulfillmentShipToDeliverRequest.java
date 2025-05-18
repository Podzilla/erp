package com.Podzilla.analytics.api.dtos.fulfillment;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;
import com.Podzilla.analytics.api.dtos.IDateRangeRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Unified request DTO for fulfillment analytics operations.
 * Contains all parameters needed for analytics API endpoints.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidDateRange
public class FulfillmentShipToDeliverRequest implements IDateRangeRequest {

    /**
     * Enum for grouping options in ship-to-deliver analytics.
     */
    public enum ShipToDeliverGroupBy {
        REGION, OVERALL, COURIER
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
    private ShipToDeliverGroupBy groupBy;
}
