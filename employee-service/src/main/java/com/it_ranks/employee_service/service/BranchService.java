package com.it_ranks.employee_service.service;

import com.it_ranks.employee_service.Entity.Branch;
import com.it_ranks.employee_service.repository.BranchRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
	private final BranchRepository branchRepository;

	public BranchService(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}
	public Branch createBranch(Branch branch) {
		return branchRepository.save(branch);
	}
}
