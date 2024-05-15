package com.example.clearsolutionstestassignment.validation.age;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, LocalDate> {
    @Value("${age.limit}")
    int minimumAge;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return true;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= minimumAge;
    }
}
