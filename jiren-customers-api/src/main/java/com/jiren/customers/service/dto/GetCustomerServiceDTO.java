package com.jiren.customers.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCustomerServiceDTO {
	String id;
	
	String businessName;
	String tradeName;
	Integer wave;
	String segment;
	WorkAreaServiceDTO workArea;
	SubWorkAreaServiceDTO subWorkArea;
	BigDecimal minAmount;	
	String channelOrigin;
	String adviser;	
	String dexCode;	
	String currency;
	Integer state;
	String stateDescription;
	LocalDate registryDate;
	LocalDate dischargeDate;
	Boolean allowElectronicInvoice;
	Boolean perfectCustomer;
	String image;
	String prefixProductCode;
	Integer erpCode;
	Boolean newCustomer;
	Boolean updatedCustomer;	
	String userAudit;

	List<BusinessServiceDTO> business;
	List<PaymentTypeServiceDTO> sellIns;
	List<PaymentTypeServiceDTO> sellOuts;	
	List<DocumentServiceDTO> documents;
	List<UserServiceDTO> users;
}
