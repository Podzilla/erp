package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;
import java.util.Date;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueSummaryRequest {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date for the revenue summary (inclusive)", example = "2023-01-01", required = true)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date for the revenue summary (inclusive)", example = "2023-01-31", required = true)
    private LocalDate endDate;

    @NotNull
    @Schema(description = "Period granularity for summary", required = true, implementation = Period.class)
    private Period period;

    public enum Period {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
