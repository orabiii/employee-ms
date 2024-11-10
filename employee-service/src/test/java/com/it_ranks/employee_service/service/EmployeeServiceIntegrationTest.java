package com.it_ranks.employee_service.service;

import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.Entity.EmployeeUpdateDTO;
import com.it_ranks.employee_service.repository.BranchRepository;
import com.it_ranks.employee_service.repository.EmployeeRepository;
import com.it_ranks.employee_service.validator.NameValidator;
import com.it_ranks.employee_service.validator.NameValidatorImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EmployeeServiceIntegrationTest {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testUpdateEmployee_SuccessfulUpdate() {
		EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
		updateDTO.setName(Optional.of("JANE"));
		updateDTO.setNationalId(Optional.of("30110101234567"));
		updateDTO.setAge(Optional.of(23));
		updateDTO.setBranchId(Optional.of(1L));

		Employee updatedEmployee = employeeService.updateEmployee(1L, updateDTO);

		assertEquals(updatedEmployee.getName(),"JANE");
		assertEquals(updatedEmployee.getNationalId(),"30110101234567");
		assertEquals(updatedEmployee.getAge(),23);
		assertEquals(updatedEmployee.getBranch().getId(),1L);
	}
	@Test
	public void testUpdateEmployee_EmployeeNotFound() {
		EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
		updateDTO.setName(Optional.of("JANE DOE"));

		assertThrows(EntityNotFoundException.class, () -> {
			employeeService.updateEmployee(-1L, updateDTO);
		});
	}
	@Test
	public void testUpdateEmployee_NameValidation() {
		EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
		updateDTO.setName(Optional.of("Jane Doe"));

		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
			employeeService.updateEmployee(1L, updateDTO);
		});
		System.out.println(exception);
		assertTrue(exception.getMessage().contains("name.uppercaseEnglish"));

	}

	@Test
	public void testUpdateEmployee_InvalidBranchId() {
		EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
		updateDTO.setBranchId(Optional.of(-1L));

		assertThrows(DataIntegrityViolationException.class, () -> {
			employeeService.updateEmployee(1L, updateDTO);
		});
	}
}
