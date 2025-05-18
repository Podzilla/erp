package com.Podzilla.analytics.validation.validators;

import com.Podzilla.analytics.api.dtos.IDateRangeRequest;
import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class DateRangeValidator implements
        ConstraintValidator<ValidDateRange, IDateRangeRequest> {
    @Override
    public boolean isValid(final IDateRangeRequest request,
            final ConstraintValidatorContext context) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return true;
        }
        return request.getEndDate().isAfter(request.getStartDate());
    }
}
