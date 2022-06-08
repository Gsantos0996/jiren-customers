package com.jiren.customers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentType {	
	String guid;
	
	String description;
	String name;
	String alternativeName;
	Boolean sellin;
	Boolean sellout;
	String mode;
	Integer index;
	Boolean active;
	
	public PaymentType(String guid) {
		this.guid = guid;
	}	
}
