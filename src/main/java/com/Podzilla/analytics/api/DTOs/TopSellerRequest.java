package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellerRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer limit;
    private SortBy sortBy;

    public enum SortBy {
        REVENUE,
        UNITS
    }
}