package com.it_ranks.api_gateway.controller;

import com.it_ranks.api_gateway.router.EmployeeProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
	private final EmployeeProxy employeeProxy;

	public Login(EmployeeProxy employeeProxy) {
		this.employeeProxy = employeeProxy;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/test")
	public String test() {
		return "jwt working";
	}
	@PostMapping("/api/branches")
	public ResponseEntity<?> createBranch(@RequestBody String name) {
		return employeeProxy.createBranch(name);
	}
}
