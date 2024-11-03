package com.it_ranks.employee_service.repository;

import com.it_ranks.employee_service.Entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
