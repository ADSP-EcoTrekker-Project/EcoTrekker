package com.ecotrekker.restapi.validation;

import com.ecotrekker.restapi.model.RouteStep;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRouteStepValidator implements ConstraintValidator<ValidRouteStep, RouteStep> {

    @Override
    public void initialize(ValidRouteStep constraintAnnotation) {
    }

    @Override
    public boolean isValid(RouteStep value, ConstraintValidatorContext context) {
        boolean startNotBlank = value.getStart() != null && value.getStart().trim().length() > 0;
        boolean endNotBlank = value.getEnd() != null && value.getEnd().trim().length() > 0;

        if (startNotBlank && endNotBlank) {
            return true;
        }

        if (value.getDistance() != null) {
            return true;
        }

        context.buildConstraintViolationWithTemplate("Either start and end or distance must be provided!")
                .addConstraintViolation();
        return false;
    }

}
