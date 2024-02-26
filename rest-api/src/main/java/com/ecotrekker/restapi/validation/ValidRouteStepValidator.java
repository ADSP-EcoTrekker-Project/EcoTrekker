package com.ecotrekker.restapi.validation;

import com.ecotrekker.restapi.model.RouteStep;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRouteStepValidator implements ConstraintValidator<ValidRouteStep, RouteStep> {

    @Override
    public void initialize(ValidRouteStep constraintAnnotation) {
    }

    public boolean isBlank(String string){
        return string == null || string.trim().length() < 0;
    }

    @Override
    public boolean isValid(RouteStep value, ConstraintValidatorContext context) {
        if ((isBlank(value.getStart()) && isBlank(value.getEnd())) && value.getDistance() == null) {
            return false;
        }

        String uri = value.getVehicle();
        if (!uri.startsWith("/") || uri.endsWith("/")) {
            return false;
        }

        context.buildConstraintViolationWithTemplate("Either start and end or distance must be provided!")
                .addConstraintViolation();
        return true;
    }

}
