package com.Podzilla.analytics.api.DTOs;

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
    private Integer limit; // Use Integer to allow null if not provided (though @RequestParam default handles this)
    private SortBy sortBy;

    public enum SortBy {
        REVENUE,
        UNITS
    }
}