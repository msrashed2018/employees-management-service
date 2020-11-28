package com.pplflw.employee.management.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.pplflw.employee.management.model.State;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstname;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastname;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "AGE", nullable = false)
	private int age;

	@Column(name = "CREATED_AT", updatable = false)
	@EqualsAndHashCode.Exclude
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(name = "UPDATED_AT")
	@EqualsAndHashCode.Exclude
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Column(name = "STATE")
	private State state = State.ADDED;

}
