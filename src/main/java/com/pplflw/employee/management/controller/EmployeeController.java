package com.pplflw.employee.management.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pplflw.employee.management.domain.Employee;
import com.pplflw.employee.management.dto.CreateEmployeeDTO;
import com.pplflw.employee.management.dto.UpdateEmployeeStateDTO;
import com.pplflw.employee.management.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	public Page<Employee> retrieveAllEmployees(Pageable pageable) {
		return employeeService.getEmployees(pageable);
	}

	@GetMapping("/employees/{employeeId}")
	public Employee retrieveEmployeeById(@PathVariable Long employeeId) {
		return employeeService.getEmployeeById(employeeId);
	}

	@DeleteMapping("/employees/{employeeId}")
	public void deleteEmployee(@PathVariable Long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeDTO createEmployeeDTO)
			throws IOException {

		Employee createdEmployee = employeeService.createEmployee(createEmployeeDTO);
		return new ResponseEntity<Employee>(createdEmployee, HttpStatus.OK);

	}

	@PutMapping("/employees/{employeeId}/state")
	public ResponseEntity<Employee> updateEmployeeState(@PathVariable Long employeeId,
			@Valid @RequestBody UpdateEmployeeStateDTO updateEmployeeStateDTO) throws IOException {

		Employee createdEmployee = employeeService.updateEmployeeState(employeeId, updateEmployeeStateDTO);
		return new ResponseEntity<Employee>(createdEmployee, HttpStatus.OK);

	}

}
