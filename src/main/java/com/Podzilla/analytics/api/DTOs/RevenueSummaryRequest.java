package com.Podzilla.analytics.api.DTOs;

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
    LocalDate  startDate;

    LocalDate  endDate;

    Period period;

    public enum Period {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}
