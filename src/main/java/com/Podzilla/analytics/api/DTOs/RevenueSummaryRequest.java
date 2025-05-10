package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueSummaryRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Period period;

    public enum Period {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
