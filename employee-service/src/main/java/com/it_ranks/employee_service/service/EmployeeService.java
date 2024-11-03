package com.it_ranks.employee_service.service;

import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.Entity.EmployeeUpdateDTO;
import com.it_ranks.employee_service.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
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
		updateDTO.getBranch().ifPresent(branch -> existingEmployee.setBranch(branch));
		return employeeRepository.save(existingEmployee);
	}

}
