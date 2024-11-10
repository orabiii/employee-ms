package com.it_ranks.employee_service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import com.it_ranks.employee_service.Entity.Branch;
import com.it_ranks.employee_service.Entity.Employee;
import jakarta.validation.ClockProvider;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AgeValidatorImpl.class})
@ExtendWith(SpringExtension.class)
class AgeValidatorImplDiffblueTest {
	@Autowired
	private AgeValidatorImpl ageValidatorImpl;

	/**
	 * Method under test:
	 * {@link AgeValidatorImpl#isValid(Employee, ConstraintValidatorContext)}
	 */
	@Test
	void testIsValid() {
		Branch branch = new Branch();
		branch.setId(1L);
		branch.setName("Name");

		Employee employee = new Employee();
		employee.setAge(1);
		employee.setBranch(branch);
		employee.setId(1L);
		employee.setName("Name");
		employee.setNationalId(null);
		ClockProvider clockProvider = mock(ClockProvider.class);
		ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(clockProvider,
				PathImpl.createRootPath(), mock(ConstraintDescriptor.class), "Constraint Validator Payload",
				ExpressionLanguageFeatureLevel.DEFAULT, ExpressionLanguageFeatureLevel.DEFAULT);

		boolean actualIsValidResult = ageValidatorImpl.isValid(employee, context);
		assertEquals(1, context.getConstraintViolationCreationContexts().size());
		assertFalse(actualIsValidResult);
	}
}
