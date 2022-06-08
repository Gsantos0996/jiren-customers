package com.jiren.customers.adapter.rest.dto.customers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class GetCustomerRestDTO {
	String id;
	String businessName;
	String tradeName;
	Integer wave;
	String segment;
	WorkAreaResponseDTO workArea;
	SubWorkAreaResponseDTO subWorkArea;
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

	List<BusinessDTO> business;
	@Valid
	List<PaymentTypeDTO> sellIns;
	@Valid
	List<PaymentTypeDTO> sellOuts;
	@Valid
	List<DocumentDTO> documents;
	@Valid
	List<UserDTO> users;
}
