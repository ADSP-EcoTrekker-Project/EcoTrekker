package com.ecotrekker.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidRouteStepValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRouteStep {
    String message() default "Invalid RouteStep";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
