package com.it_ranks.employee_service.repository;

import com.it_ranks.employee_service.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

 public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
