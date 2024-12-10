package com.digitinary.accounts.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.digitinary.accounts.validator.AccountNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = AccountNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccountNumber {

	String message() default "Invalid account number";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
