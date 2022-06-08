package com.jiren.customers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentType {	
	String guid;
	Customer customer;
	PaymentType paymentType;

	Integer type;
	Boolean active;
	
	@Override
	public String toString() {
		return "CustomerPaymentType [guid=" + guid + ", type=" + type + ", active=" + active + "]";
	}
	
	
}
