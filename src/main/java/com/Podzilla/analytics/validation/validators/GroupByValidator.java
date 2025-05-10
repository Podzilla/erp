package com.Podzilla.analytics.validation.validators;

import java.util.Arrays;

import com.Podzilla.analytics.validation.annotations.ValidGroupBy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Validator for the groupBy field that checks if it contains a valid value
 * based on the current endpoint.
 */
@Component
public class GroupByValidator
        implements ConstraintValidator<ValidGroupBy, String> {

    private String[] placeToShipValues;
    private String[] shipToDeliverValues;

    @Override
    public void initialize(final ValidGroupBy constraintAnnotation) {
        this.placeToShipValues = constraintAnnotation.placeToShipValues();
        this.shipToDeliverValues = constraintAnnotation.shipToDeliverValues();
    }

    @Override
    public boolean isValid(final String value,
            final ConstraintValidatorContext context) {
        // If no value provided, let @NotNull handle it
        if (value == null) {
            return true;
        }

        // Get the current path from the request
        ServletRequestAttributes attr =
                (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        // If no request context available (e.g. in unit tests), skip validation
        if (attr == null) {
            return true;
        }

        HttpServletRequest request = attr.getRequest();
        String path = request.getRequestURI();

        // Determine which values to check against based on the path
        if (path.contains("/place-to-ship-time")) {
            return Arrays.asList(placeToShipValues).contains(value);
        } else if (path.contains("/ship-to-deliver-time")) {
            return Arrays.asList(shipToDeliverValues).contains(value);
        }

        // Default to true for unknown paths (should never happen in our case)
        return true;
    }
}
