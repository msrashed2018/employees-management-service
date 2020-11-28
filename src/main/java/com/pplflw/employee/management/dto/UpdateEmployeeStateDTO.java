package com.pplflw.employee.management.dto;

import javax.validation.constraints.NotNull;

import com.pplflw.employee.management.model.State;

import lombok.Data;

@Data
public class UpdateEmployeeStateDTO {

	@NotNull(message = "state must not be null")
	private State state;

}
