package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue; // Import AssertTrue
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request parameters for fetching revenue by category")
public class RevenueByCategoryRequest {

    @NotNull(message = "Start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Start date for the revenue report (inclusive)", example = "2023-01-01", required = true)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "End date for the revenue report (inclusive)", example = "2023-01-31", required = true)
    private LocalDate endDate;

    @AssertTrue(message = "End date must be equal to or after start date")
    private boolean isEndDateOnOrAfterStartDate() {
      
        if (startDate == null || endDate == null) {
            return true;
        }

        return !endDate.isBefore(startDate);
    }
}