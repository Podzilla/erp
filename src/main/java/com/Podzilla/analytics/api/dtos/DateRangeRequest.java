package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ValidDateRange
@Getter
@AllArgsConstructor
public class DateRangeRequest {

    @NotNull(message = "startDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
