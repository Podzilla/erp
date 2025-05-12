package com.Podzilla.analytics.validation.validators;

import java.time.LocalDate;
import java.lang.reflect.Method;

import com.Podzilla.analytics.validation.annotations.ValidDateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class DateRangeValidator implements
        ConstraintValidator<ValidDateRange, Object> {
    @Override
    public boolean isValid(final Object value,
            final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Method getStartDate = value.getClass().getMethod("getStartDate");
            Method getEndDate = value.getClass().getMethod("getEndDate");
            LocalDate startDate = (LocalDate) getStartDate.invoke(value);
            LocalDate endDate = (LocalDate) getEndDate.invoke(value);
            if (startDate == null || endDate == null) {
                return true; // Let @NotNull handle this
            }
            return !endDate.isBefore(startDate);
        } catch (Exception e) {
            return false;
        }
    }
}
