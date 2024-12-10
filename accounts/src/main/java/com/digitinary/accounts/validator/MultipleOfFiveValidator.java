package com.digitinary.accounts.validator;

import com.digitinary.accounts.annotation.MultipleOfFive;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleOfFiveValidator implements ConstraintValidator<MultipleOfFive, Double> {

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value % 5 == 0;
	}

}
