package com.pplflw.employee.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public String checkStatus() {

		return "Employee Management API is up and running";
	}

}
