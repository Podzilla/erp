package com.Podzilla.analytics.validation.validators;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for ensuring valid date ranges in DateRangeRequest objects.
 */
public final class DateRangeValidator implements
        ConstraintValidator<ValidDateRange, DateRangeRequest> {

    /**
     * Validates that the end date is after the start date in a date range
     *  request.
     *
     * @param request the date range request to validate
     * @param context the validation context
     * @return true if the date range is valid or if dates are null,
     *  false otherwise
     */
    @Override
    public boolean isValid(final DateRangeRequest request,
            final ConstraintValidatorContext context) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return true;
        }
        return request.getEndDate().isAfter(request.getStartDate());
    }
}
