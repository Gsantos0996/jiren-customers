package com.jiren.customers.service.dto.wb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetUserDTO {

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
