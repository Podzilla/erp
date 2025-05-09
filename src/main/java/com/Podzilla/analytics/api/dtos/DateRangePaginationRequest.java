package com.Podzilla.analytics.api.dtos;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.Podzilla.analytics.validation.annotations.ValidDateRange;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@ValidDateRange
@Getter
@AllArgsConstructor
public class DateRangePaginationRequest {

    @NotNull(message = "startDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Start date and time of the range "
            + "(inclusive)", example = "2024-01-01T00:00:00", required = true)
    private LocalDateTime startDate;

    @NotNull(message = "endDate is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "End date and time of the range "
            + "(inclusive)", example = "2024-01-31T23:59:59", required = true)
    private LocalDateTime endDate;

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
