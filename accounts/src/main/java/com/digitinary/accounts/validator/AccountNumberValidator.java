package com.digitinary.accounts.validator;

import com.digitinary.accounts.annotation.ValidAccountNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<ValidAccountNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        String accountNumberRegex = "^\\d{7}\\d{3}$";
        return value.matches(accountNumberRegex);
	}

}
