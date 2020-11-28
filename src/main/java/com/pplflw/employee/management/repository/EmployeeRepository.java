package com.pplflw.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pplflw.employee.management.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
