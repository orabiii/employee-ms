package com.it_ranks.employee_service.service;

import com.it_ranks.employee_service.Entity.Branch;
import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.Entity.EmployeeUpdateDTO;
import com.it_ranks.employee_service.repository.BranchRepository;
import com.it_ranks.employee_service.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;
	private final BranchRepository branchRepository;

	public EmployeeService(EmployeeRepository employeeRepository, BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll(); 
	}
	public Optional<Employee> findById(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee;
	}

	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	public Employee updateEmployee(Long id, EmployeeUpdateDTO updateDTO) {
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));

		updateDTO.getName().ifPresent(name -> existingEmployee.setName(name));
		updateDTO.getNationalId().ifPresent(nationalId -> existingEmployee.setNationalId(nationalId));
		updateDTO.getAge().ifPresent(age -> existingEmployee.setAge(age));
		updateDTO.getBranchId().ifPresent(branchId -> {
			Branch branch = branchRepository.findById(branchId)
					.orElseThrow(() -> new DataIntegrityViolationException("branch.notFound"));
			existingEmployee.setBranch(branch);
		});
		return employeeRepository.save(existingEmployee);
	}
	public Employee createEmployeeDto(EmployeeUpdateDTO employeeUpdateDTO) {
		Employee employee = new Employee();

		employeeUpdateDTO.getName().ifPresent(employee::setName);
		employeeUpdateDTO.getNationalId().ifPresent(employee::setNationalId);
		employeeUpdateDTO.getAge().ifPresent(employee::setAge);
		employeeUpdateDTO.getBranchId().ifPresent(branchId -> {
			Branch branch = branchRepository.findById(branchId)
					.orElseThrow(() -> new DataIntegrityViolationException("Branch not found"));
			employee.setBranch(branch);
		});

		return employeeRepository.save(employee);
	}

}
