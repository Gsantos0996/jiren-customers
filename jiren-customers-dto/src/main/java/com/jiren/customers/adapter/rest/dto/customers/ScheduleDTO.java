package com.jiren.customers.adapter.rest.dto.customers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
	String id;
	@NotBlank(message = "Schedule: day cannot be blank")
	@NotNull(message = "Schedule: day is required")
	@Size(max = 16, message = "Schedule: day value between 0 and 16")
	String day;
	@NotBlank(message = "Schedule: startTime cannot be blank")
	@NotNull(message = "Schedule: startTime is required")
	@Size(max = 16, message = "Schedule: startTime value between 0 and 16")
	String startTime;
	@NotBlank(message = "Schedule: endTime cannot be blank")
	@NotNull(message = "Schedule: endTime is required")
	@Size(max = 16, message = "Schedule: endTime value between 0 and 16")
	String endTime;
	@NotNull(message = "Schedule: attention is required")
	Boolean attention;
}
