package com.jiren.customers.service.dto.wb;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetAddressDTO {

	String id;
	
	String address;
	String reference;
	BigDecimal latitude;	
	BigDecimal longitude;
	Integer zone;	
	SheetTimeDTO timeWindow1;
	SheetTimeDTO timeWindow2;
	String postalCode;
	String ubigeoCode;	
}
