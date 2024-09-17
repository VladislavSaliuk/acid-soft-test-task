package com.example.acidsofttesttask.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublicationYearValidator.class)
public @interface ValidPublicationYear {
    String message() default "Publication year cannot be greater than the current year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

