package com.it_ranks.employee_service.Controller;

import com.it_ranks.employee_service.Entity.Branch;
import com.it_ranks.employee_service.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branches")
public class BranchController {
	private final BranchService branchService;

	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}

	@PostMapping
	public ResponseEntity<?> createBranch(@RequestBody String name) {
		Branch branch = new Branch();
		branch.setName(name);
		Branch createdbranch = branchService.createBranch(branch);
		return ResponseEntity.ok().body(createdbranch);
	}
}
