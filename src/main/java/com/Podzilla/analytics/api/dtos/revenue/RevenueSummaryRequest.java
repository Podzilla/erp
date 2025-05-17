package com.Podzilla.analytics.api.dtos.revenue;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ValidDateRange
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request parameters for revenue summary")
public class RevenueSummaryRequest {

    @NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date for the revenue summary (inclusive)",
     example = "2023-01-01", required = true)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date for the revenue summary (inclusive)",
     example = "2023-01-31", required = true)
    private LocalDate endDate;

    @NotNull(message = "Period is required")
    @Schema(description = "Period granularity for summary",
     required = true, implementation = Period.class)
    private Period period;

    public enum Period {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
