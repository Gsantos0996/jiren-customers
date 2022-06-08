package com.jiren.legalsections.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Customer {	
	String guid;
	
	String businessName;
	String tradeName;
	Integer wave;
	String segment;
	String workArea;
	String subWorkArea;
	BigDecimal minAmount;	
	String channelOrigin;
	String adviser;	
	String dexCode;	
	String currency;
	Integer state;
	LocalDate registryDate;
	LocalDate dischargeDate;
	Boolean allowElectronicInvoice;
	Boolean perfectCustomer;
	String image;
	String prefixProductCode;
	Integer erpCode;
	Boolean newCustomer;
	Boolean updatedCustomer;
	Boolean active;
	
	String userCreated;
	LocalDateTime dateCreated;
	String userUpdated;
	LocalDateTime dateUpdated;
	
}
