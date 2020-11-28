/**
 * 
 */
package com.pplflw.employee.management.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pplflw.employee.management.domain.Employee;
import com.pplflw.employee.management.dto.CreateEmployeeDTO;
import com.pplflw.employee.management.dto.UpdateEmployeeStateDTO;
import com.pplflw.employee.management.exception.BusinessException;
import com.pplflw.employee.management.exception.ResourceNotFoundException;
import com.pplflw.employee.management.model.State;
import com.pplflw.employee.management.repository.EmployeeRepository;
import com.pplflw.employee.management.service.EmployeeService;

/**
 * @author mohamedsalah
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Page<Employee> getEmployees(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> Employee = employeeRepository.findById(id);
		if (!Employee.isPresent()) {
			throw new ResourceNotFoundException("EmployeeId " + id + " not found");
		}
		return Employee.get();
	}

	@Override
	public Employee createEmployee(CreateEmployeeDTO createEmployeeDTO) {
		Employee employee = new Employee();
		employee.setFirstname(createEmployeeDTO.getFirstname());
		employee.setLastname(createEmployeeDTO.getLastname());
		employee.setEmail(createEmployeeDTO.getEmail());
		employee.setAge(createEmployeeDTO.getAge());
		employee.setState(State.ADDED);
		employee.setCreatedAt(LocalDateTime.now());
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(long employeeId) {
		if (!employeeRepository.existsById(employeeId)) {
			throw new ResourceNotFoundException("EmployeeId " + employeeId + " not found");
		}
		employeeRepository.deleteById(employeeId);

	}

	@Override
	public Employee updateEmployeeState(long employeeId, UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		Employee employee = this.getEmployeeById(employeeId);
		State currentState = employee.getState();
		State newState = updateEmployeeStateDTO.getState();
		verifyUpdatedState(currentState, newState);
		employee.setState(newState);
		return employeeRepository.save(employee);
	}

	private void verifyUpdatedState(State currentState, State newState) {
		if ((newState.equals(State.IN_CHECK) && !currentState.equals(State.ADDED))
				|| (newState.equals(State.APPROVED) && !currentState.equals(State.IN_CHECK))
				|| (newState.equals(State.ACTIVE) && !currentState.equals(State.APPROVED))
				|| newState.equals(State.ADDED)

		) {
			throw new BusinessException(
					"ILLEGAL_STATE_CHANGE: Employee State Can't be changed from " + currentState + " into " + newState);
		}
	}

}
