package com.jiren.paymenttype.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Override
	public String toString() {
		return "PaymentType [guid=" + guid + ", description=" + description + ", name=" + name + ", alternativeName="
				+ alternativeName + ", sellin=" + sellin + ", sellout=" + sellout + ", mode=" + mode + ", index="
				+ index + ", active=" + active + "]";
	}	
}
