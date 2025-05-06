package com.Podzilla.analytics.validation.validators;

import com.Podzilla.analytics.api.dtos.DateRangeRequest;
import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class DateRangeValidator implements
        ConstraintValidator<ValidDateRange, DateRangeRequest> {
    @Override
    public boolean isValid(final DateRangeRequest request,
            final ConstraintValidatorContext context) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return true;
        }
        return request.getEndDate().isAfter(request.getStartDate());
    }
}
