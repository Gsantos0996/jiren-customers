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
public class Ubigeo {	
	String guid;
	
	String district;
	String province;
	String department;
	
	public Ubigeo(String guid) {
		this.guid = guid;
	}	
}
