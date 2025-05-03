package com.Podzilla.analytics.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DatetimeFormatter {
    public static LocalDateTime convertStartDateToDatetime(LocalDate startDate) {
        return startDate.atStartOfDay();
    }
    public static LocalDateTime convertEndDateToDatetime(LocalDate endDate) {
        return endDate.atTime(LocalTime.MAX);
    }
}
