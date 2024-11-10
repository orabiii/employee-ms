package com.it_ranks.employee_service.service;

import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.Entity.EmployeeUpdateDTO;
import com.it_ranks.employee_service.repository.BranchRepository;
import com.it_ranks.employee_service.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private BranchRepository branchRepository;
	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUpdateEmployee_Success() {
		Long employeeId = 1L;
		Employee existingEmployee = new Employee();
		existingEmployee.setId(employeeId);
		existingEmployee.setName("John");
		existingEmployee.setNationalId("12345678901234");
		existingEmployee.setAge(30);

		EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
		updateDTO.setName(Optional.of("Updated Name"));
		updateDTO.setAge(Optional.of(35));

		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		Employee updatedEmployee = employeeService.updateEmployee(employeeId, updateDTO);

		assertEquals("Updated Name", updatedEmployee.getName());
		assertEquals(35, updatedEmployee.getAge());

	}

	@Test
	void testUpdateEmployee_EmployeeNotFound() {
		Long employeeId = 1L;
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
			employeeService.updateEmployee(employeeId, new EmployeeUpdateDTO());
		});
		assertEquals("Employee not found", exception.getMessage());
	}
}
