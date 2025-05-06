package com.Podzilla.analytics.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * A generic structure for sending error responses from the API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /** The time when the error occurred. */
    private LocalDateTime timestamp;

    /** The HTTP status code. */
    private int status;

    /** The error type (e.g., "Bad Request"). */
    private String error;

    /** A human-readable error message. */
    private String message;

    /** Field-specific validation errors, if any. */
    private Map<String, String> fieldErrors;

    /** The request path that triggered the error. */
    private String path;
}
