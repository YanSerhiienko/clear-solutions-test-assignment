package com.example.clearsolutionstestassignment.validation.age;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeLimitValidator.class)
public @interface AgeLimit {
    String message() default "User is too young";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
