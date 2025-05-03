package com.Podzilla.analytics.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
}
