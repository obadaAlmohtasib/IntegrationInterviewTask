package com.digitinary.accounts.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.digitinary.accounts.validator.MultipleOfFiveValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = MultipleOfFiveValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleOfFive {
	
    String message() default "Number must be a multiple of 5.";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}