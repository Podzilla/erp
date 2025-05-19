package com.Podzilla.analytics.api.dtos;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.Podzilla.analytics.validation.annotations.ValidPagination;

import io.swagger.v3.oas.annotations.media.Schema;

@ValidPagination
@Data
@AllArgsConstructor
public class PaginationRequest implements IPaginationRequest {

    @Min(value = 0, message = "Page number "
            + "must be greater than or equal to 0")
    @Schema(description = "Page number "
            + "(zero-based)", example = "0", required = true)
    private int page;

    @Min(value = 1, message = "Page size "
            + "must be greater than 0")
    @Schema(description = "Number of "
            + "items per page", example = "20", required = true)
    private int size;
}
