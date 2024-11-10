package com.it_ranks.employee_service;

import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.validator.AgeValidatorImpl;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployeeServiceApplicationTests {

	private AgeValidatorImpl ageValidator;
	private ConstraintValidatorContext context;

	@BeforeEach
	void setUp() {
		ageValidator = new AgeValidatorImpl();
		context = mock(ConstraintValidatorContext.class);
		ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
		when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
	}
	@Test
	void testIsValid_withValidNationalIdAndAge() {
		Employee employee = new Employee();
		employee.setNationalId("29912300123456");
		employee.setAge(24);
		boolean result = ageValidator.isValid(employee, context);
		assertTrue(result);
	}
	@Test
	void testIsValid_withInvalidFormatNationalId() {
		// Arrange
		Employee employee = new Employee();
		employee.setNationalId("29912");
		employee.setAge(25);

		// Act
		boolean result = ageValidator.isValid(employee, context);

		// Assert
		assertFalse(result);
		verify(context).buildConstraintViolationWithTemplate("nationalId.invalidFormat");
	}
	@Test
	void testIsValid_withNullNationalId() {
		Employee employee = new Employee();
		employee.setNationalId(null);
		employee.setAge(25);

		boolean result = ageValidator.isValid(employee, context);

		assertFalse(result);
		verify(context).buildConstraintViolationWithTemplate("nationalId.notNull");
	}
	@Test
	void testIsValid_withMismatchedAge() {
		Employee employee = new Employee();
		employee.setNationalId("29912300123456");
		employee.setAge(23);

		boolean result = ageValidator.isValid(employee, context);
		assertFalse(result);
	}

}
