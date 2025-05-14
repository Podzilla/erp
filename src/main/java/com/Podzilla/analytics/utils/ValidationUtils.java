package com.Podzilla.analytics.utils;

import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

public class ValidationUtils {

    /**
     * Validates date range parameters and returns a ResponseEntity with error if validation fails.
     * @param startDate The start date to validate
     * @param endDate The end date to validate
     * @return ResponseEntity with error if validation fails, null if validation passes
     */
    public static <T> ResponseEntity<T> validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }

        return null;
    }

    /**
     * Validates that a numeric limit parameter is positive.
     * @param limit The limit to validate
     * @return ResponseEntity with error if validation fails, null if validation passes
     */
    public static <T> ResponseEntity<T> validatePositiveLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return null;
    }

    /**
     * Validates that an enum parameter is not null.
     * @param enumValue The enum value to validate
     * @return ResponseEntity with error if validation fails, null if validation passes
     */
    public static <T> ResponseEntity<T> validateEnumNotNull(Enum<?> enumValue) {
        if (enumValue == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return null;
    }
}