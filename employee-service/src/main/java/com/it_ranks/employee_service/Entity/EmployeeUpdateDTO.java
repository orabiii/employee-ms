package com.it_ranks.employee_service.Entity;

import lombok.Data;
import java.util.Optional;
@Data
public class EmployeeUpdateDTO {
	private Optional<String> name = Optional.empty();
	private Optional<String> nationalId = Optional.empty();
	private Optional<Integer> age = Optional.empty();
	private Optional<Long> branchId = Optional.empty();

}
