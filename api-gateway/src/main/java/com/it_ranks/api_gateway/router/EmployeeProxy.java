package com.it_ranks.api_gateway.router;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "employee-service", configuration = FeignConfig.class)
public interface EmployeeProxy {
	@PostMapping("api/branches")
	public Mono<ResponseEntity<?>> createBranch(@RequestBody String branchName);

	}
