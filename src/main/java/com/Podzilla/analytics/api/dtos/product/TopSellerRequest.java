package com.Podzilla.analytics.api.dtos.product;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;


import com.Podzilla.analytics.validation.annotations.ValidDateRange;
import com.Podzilla.analytics.api.dtos.IDateRangeRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ValidDateRange
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellerRequest implements IDateRangeRequest {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date for the report (inclusive)",
     example = "2024-01-01", required = true)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date for the report (inclusive)",
     example = "2024-01-31", required = true)
    private LocalDate endDate;

    @NotNull
    @Positive
    @Schema(description = "Maximum number of top sellers to return",
     example = "10", required = true)
    private Integer limit;

    @NotNull
    @Schema(description = "Sort by revenue or units", required = true,
     implementation = SortBy.class)
    private SortBy sortBy;

    public enum SortBy {
        @Schema(description = "Sort by total revenue")
        REVENUE,
        @Schema(description = "Sort by total units sold")
        UNITS
    }
}
