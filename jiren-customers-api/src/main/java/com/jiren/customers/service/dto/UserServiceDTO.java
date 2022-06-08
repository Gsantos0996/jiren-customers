package com.jiren.customers.service.dto;

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
public class UserServiceDTO {
	String id;
	
	String typeDocument;
	String numberDocument;
	String name;
	String firstLastName;	
	String secondLastName;	
	String email;
	String phone;
	Boolean owner;
}
