package com.jiren.customers.domain.model;

import java.time.LocalDateTime;

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
public class LegalSections {	
	String guid;
	Customer customer;
	
	String type;
	String legalDescription;
	LocalDateTime date;
	Boolean active;
}
