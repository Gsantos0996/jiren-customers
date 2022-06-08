package com.jiren.customers.adapter.rest.dto.customers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
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
public class BusinessDTO {
	String id;
	
	Integer deliveryDistance;
	BigDecimal deliveryCost;
	@NotBlank(message = "Business: ubigeoCode cannot be blank")
	@NotNull(message = "Business: ubigeoCode is required")
	String ubigeoCode;
	@NotBlank(message = "Business: address cannot be blank")
	@NotNull(message = "Business: address is required")
	@Size(max = 255, message = "Business: address value between 0 and 255")
	String address;
	@Size(max = 255, message = "Business: postalCode value between 0 and 255")
	String postalCode;
	@Size(max = 255, message = "Business: reference value between 0 and 255")
	String reference;
	@NotNull(message = "Business: latitude is required")
	BigDecimal latitude;
	@NotNull(message = "Business: longitude is required")
	BigDecimal longitude;	
	Integer zone;
	@Size(max = 255, message = "Business: annex value between 0 and 255")
	String annex;
	
	@Valid
	List<ScheduleDTO> schedules;
	@Valid
	List<TimeWindowDTO> timeWindows;
}
