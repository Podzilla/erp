package com.Podzilla.analytics.validation.validators;

import com.Podzilla.analytics.api.dtos.IPaginationRequest;
import com.Podzilla.analytics.validation.annotations.ValidPagination;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class PaginationValidator implements
    ConstraintValidator<ValidPagination, IPaginationRequest> {

    @Override
    public boolean isValid(final IPaginationRequest request,
            final ConstraintValidatorContext context) {
        return request.getPage() >= 0 && request.getSize() > 0;
    }

}
