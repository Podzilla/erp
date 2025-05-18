package com.Podzilla.analytics.api.dtos.revenue;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
public class RevenueSummaryResponse {
    @Schema(description = "Start date of the period for the revenue summary",
     example = "2023-01-01")
    private LocalDate periodStartDate;

    @Schema(description = "Total revenue for the specified period",
     example = "12345.67")
    private BigDecimal totalRevenue;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDate periodStartDate;
        private BigDecimal totalRevenue;
        public Builder() { }

        public Builder periodStartDate(final LocalDate periodStartDate) {
            this.periodStartDate = periodStartDate;
            return this;
        }

        public Builder totalRevenue(final BigDecimal totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public RevenueSummaryResponse build() {
            return new RevenueSummaryResponse(periodStartDate, totalRevenue);
        }
    }
}

