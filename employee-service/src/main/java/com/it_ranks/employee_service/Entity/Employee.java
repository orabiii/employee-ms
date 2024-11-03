package com.it_ranks.employee_service.Entity;

import com.it_ranks.employee_service.validator.AgeValidator;
import com.it_ranks.employee_service.validator.NameValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@AgeValidator
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Name.notNull")
	@NameValidator
	private String name;

	//@NotNull(message = "nationalId.notNull")
	@Size(min = 14, max = 14, message = "nationalId.length")
	@Column(unique = true)
	private String nationalId;

	private int age;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;
}
