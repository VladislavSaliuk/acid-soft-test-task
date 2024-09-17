package com.example.acidsofttesttask.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PublicationYearValidator implements ConstraintValidator<ValidPublicationYear, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        int currentYear = LocalDate.now().getYear();
        return value <= currentYear;
    }
}
