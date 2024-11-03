package com.it_ranks.employee_service.validator;

import com.it_ranks.employee_service.Entity.Employee;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidatorImpl implements ConstraintValidator<AgeValidator, Employee> {

	@Override
	public boolean isValid(Employee employee, ConstraintValidatorContext context) {
		String nationalId = employee.getNationalId();
		if(nationalId == null){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("nationalId.notNull")
					.addConstraintViolation();
			return false;
		}
		Integer age = employee.getAge();
		int century = Character.getNumericValue(nationalId.charAt(0));
		int yearPrefix = Integer.parseInt(nationalId.substring(1, 3));
		int month = Integer.parseInt(nationalId.substring(3, 5));
		int day = Integer.parseInt(nationalId.substring(5, 7));

		int year = (century == 2 ? 1900 : 2000) + yearPrefix;
		LocalDate birthDate;
		try {
			birthDate = LocalDate.of(year, month, day);
		} catch (Exception e) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("nationalId.invalidFormat")
					.addConstraintViolation();
			return false;
		}
		int derivedAge = Period.between(birthDate, LocalDate.now()).getYears();
		return derivedAge == age;
	}
}