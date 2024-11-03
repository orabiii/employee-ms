package com.it_ranks.employee_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidatorImpl implements ConstraintValidator<NameValidator, String> {

	@Override
	public boolean isValid(String s, ConstraintValidatorContext context) {
		return s != null && s.matches("^[A-Z\\s\\p{Punct}]+$");
	}

}
