package com.ecotrekker.restapi.validation;

import com.ecotrekker.restapi.model.RouteStep;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ValidRouteStepValidator implements ConstraintValidator<ValidRouteStep, RouteStep> {

    private static final List<String> lineVehicles = Arrays.asList("bus", "e-bus", "ice-bus", "metro", "tram", "train");

    @Override
    public void initialize(ValidRouteStep constraintAnnotation) {
    }

    public boolean isBlank(String string){
        return string == null || string.trim().isEmpty();
    }

    @Override
    public boolean isValid(RouteStep value, ConstraintValidatorContext context) {
        if ((isBlank(value.getStart()) && isBlank(value.getEnd())) && value.getDistance() == null) {
            context.buildConstraintViolationWithTemplate("Either start and end or distance must be provided!")
                    .addConstraintViolation();
            return false;
        }

        if (lineVehicles.contains(value.getVehicle()) && value.getDistance() == null && isBlank(value.getLine())) {
            context.buildConstraintViolationWithTemplate("For this vehicle either distance or the line must be provided!")
                    .addConstraintViolation();
            return false;
        }

        String uri = value.getVehicle();
        if (!uri.startsWith("/") || uri.endsWith("/")) {
            return false;
        }


        return true;
    }

}
