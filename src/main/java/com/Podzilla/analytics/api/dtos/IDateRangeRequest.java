package com.Podzilla.analytics.api.dtos;
import java.time.LocalDate;


public interface IDateRangeRequest {
    LocalDate getStartDate();
    LocalDate getEndDate();
}
