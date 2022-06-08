package com.jiren.customers.service.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessServiceDTO {
	String id;
	
	Integer deliveryDistance;
	BigDecimal deliveryCost;
	String ubigeoCode;
	String address;
	String postalCode;
	String reference;
	BigDecimal latitude;
	BigDecimal longitude;
	Integer zone;
	String annex;
	
	List<ScheduleServiceDTO> schedules;
	List<TimeWindowServiceDTO> timeWindows;
}
