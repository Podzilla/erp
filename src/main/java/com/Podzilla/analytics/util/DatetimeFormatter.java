package com.Podzilla.analytics.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Instant;
import java.time.ZoneId;

public class DatetimeFormatter {
    public static LocalDateTime convertStartDateToDatetime(
        final LocalDate startDate
    ) {
        return startDate.atStartOfDay();
    }
    public static LocalDateTime convertEndDateToDatetime(
        final LocalDate endDate
    ) {
        return endDate.atTime(LocalTime.MAX);
    }
    public static LocalDateTime convertIntsantToDateTime(
        final Instant timestamp
    ) {
        return LocalDateTime.ofInstant(
            timestamp,
            ZoneId.systemDefault()
        );
    }
}
