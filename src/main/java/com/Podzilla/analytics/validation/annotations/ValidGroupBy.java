package com.Podzilla.analytics.validation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.Podzilla.analytics.validation.validators.GroupByValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that the groupBy parameter contains a valid value
 * for the corresponding endpoint.
 */
@Documented
@Constraint(validatedBy = GroupByValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGroupBy {
    String message() default "Invalid groupBy value. Must be a valid enum "
            + "value for the endpoint";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] placeToShipValues() default {"OVERALL", "REGION"};
    String[] shipToDeliverValues() default {"OVERALL", "REGION", "COURIER"};
}
