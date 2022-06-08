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
public class TimeWindowDTO {
	String id;
	@NotBlank(message = "TimeWindow: startTime cannot be blank")
	@NotNull(message = "TimeWindow: startTime is required")
	@Size(max = 16, message = "TimeWindow: startTime value between 0 and 16")
	String startTime;
	@NotBlank(message = "TimeWindow: endTime cannot be blank")
	@NotNull(message = "TimeWindow: endTime is required")
	@Size(max = 16, message = "TimeWindow: endTime value between 0 and 16")
	String endTime;
}
