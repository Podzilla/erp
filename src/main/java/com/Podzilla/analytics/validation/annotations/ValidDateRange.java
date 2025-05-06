package com.Podzilla.analytics.validation.annotations;

import com.Podzilla.analytics.validation.validators.DateRangeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate that an end date is after a start date in
 * a date range.
 */
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    /**
     * Defines the default error message when validation fails.
     *
     * @return the error message
     */
    String message() default "endDate must be after startDate";

    /**
     * Specifies the validation groups this constraint belongs to.
     *
     * @return the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload objects for additional validation information.
     *
     * @return the payload classes
     */
    Class<? extends Payload>[] payload() default {};
}
