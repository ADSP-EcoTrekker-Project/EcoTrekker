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
        if (value.getStart() != null && value.getStart().trim().length() > 0
                && value.getEnd() != null && value.getEnd().trim().length() > 0) {
            return true;
        }

        if (value.getDistance() != null) {
            if (value.getDistance() > 0L) {
                return true;
            } else {
                context.buildConstraintViolationWithTemplate("Distance must be a positive number, or start and end location must be provided!")
                        .addConstraintViolation();
                return false;
            }
        }

        context.buildConstraintViolationWithTemplate("Either start and end or distance must be provided!")
                .addConstraintViolation();
        return false;
    }

}
