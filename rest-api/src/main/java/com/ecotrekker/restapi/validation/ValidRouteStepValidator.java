package com.ecotrekker.restapi.validation;

import com.ecotrekker.restapi.model.RouteStep;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

public class ValidRouteStepValidator implements ConstraintValidator<ValidRouteStep, RouteStep> {

    @Override
    public void initialize(ValidRouteStep constraintAnnotation) {
    }

    @Override
    public boolean isValid(RouteStep value, ConstraintValidatorContext context) {
        if (isNotBlank(value.getStart()) && isNotBlank(value.getEnd())) {
            return true;
        }

        if (value.getDistance() != null) {
            if (value.getDistance() > LONG_ZERO) {
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
