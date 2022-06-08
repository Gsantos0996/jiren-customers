package com.jiren.customers.domain.model;

import java.math.BigDecimal;
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
public class CustomerCredit {	
	String guid;
	Customer customer;
	
	Integer erpCode;
	BigDecimal totalLine;	
	BigDecimal availableLine;	
	BigDecimal reservedLine;	
	LocalDateTime expirationDate;	
	Integer deadlineDays;
	Boolean active;
	
	String userCreated;
	LocalDateTime dateCreated;
	String userUpdated;
	LocalDateTime dateUpdated;
}
