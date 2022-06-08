package com.jiren.customers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {	
	String guid;
	Customer customer;
	
	String type;
	String number;
	Boolean docFiscal;
	Boolean active;
	
	@Override
	public String toString() {
		return "Document [guid=" + guid + ", type=" + type + ", number=" + number + ", docFiscal=" + docFiscal
				+ ", active=" + active + "]";
	}
	
	
}
