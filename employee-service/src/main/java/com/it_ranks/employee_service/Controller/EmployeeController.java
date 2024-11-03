package com.it_ranks.employee_service.Controller;

import com.it_ranks.employee_service.Entity.Employee;
import com.it_ranks.employee_service.Entity.EmployeeUpdateDTO;
import com.it_ranks.employee_service.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	@GetMapping
	public List<Employee> getAllEmployee() {
		return employeeService.findAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
		Optional<Employee> employee = employeeService.findById(id);
		if (!employee.isPresent()) {
			return ResponseEntity.status(404).body("Employee with ID " + id + " does not exist.");
		}
		return ResponseEntity.ok(employee.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
		Optional<Employee> employee = employeeService.findById(id);
		if (!employee.isPresent()) {
			return ResponseEntity.status(404).body("Employee with ID " + id + " does not exist.");
		}
		employeeService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
			Employee savedEmployee = employeeService.createEmployee(employee);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedEmployee.getId())
					.toUri();
			return ResponseEntity.created(location).body(savedEmployee);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateEmployee(@PathVariable Long id,@RequestBody EmployeeUpdateDTO updateDTO) {
		Employee updatedEmployee = employeeService.updateEmployee(id, updateDTO);
		return ResponseEntity.ok(updatedEmployee);
	}
}
