package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@ValidDateRange
@Getter
@AllArgsConstructor
public class DateRangeRequest {

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
}
