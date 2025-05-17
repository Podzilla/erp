package com.Podzilla.analytics.api.dtos;

import java.time.LocalDate;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;
import com.Podzilla.analytics.validation.annotations.ValidPagination;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@ValidDateRange
@ValidPagination
@Getter
@AllArgsConstructor
public class DateRangePaginationRequest
        implements IDateRangeRequest, IPaginationRequest {

    @NotNull(message = "startDate is required")
    @Schema(description = "Start date of the range "
            + "(inclusive)", example = "2024-01-01", required = true)
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @Schema(description = "End date of the range "
            + "(inclusive)", example = "2024-01-31", required = true)
    private LocalDate endDate;

    @Min(value = 0, message = "Page "
            + "number must be greater than or equal to 0")
    @Schema(description = "Page number "
            + "(zero-based)", example = "0", required = true)
    private int page;

    @Min(value = 1, message = "Page "
            + "size must be greater than 0")
    @Schema(description = "Number of "
            + "items per page", example = "20", required = true)
    private int size;
}
