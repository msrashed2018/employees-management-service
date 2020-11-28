package com.pplflw.employee.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pplflw.employee.management.domain.Employee;
import com.pplflw.employee.management.dto.CreateEmployeeDTO;
import com.pplflw.employee.management.dto.UpdateEmployeeStateDTO;

public interface EmployeeService {

	Page<Employee> getEmployees(Pageable pageable);

	Employee getEmployeeById(long employeeId);

	Employee createEmployee(CreateEmployeeDTO createEmployeeDTO);

	Employee updateEmployeeState(long employeeId, UpdateEmployeeStateDTO updateEmployeeStateDTO);

	void deleteEmployee(long employeeId);
}
