package com.jiren.customers.adapter.rest.dto.customers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerRestDTO {
	String id;
	  
	@NotBlank(message = "Customer: businessName cannot be blank")
	@NotNull(message = "Customer: businessName is required")
	@Size(max = 255, message = "Customer: businessName value between 0 and 255")
	String businessName;
	@Size(max = 255, message = "Customer: tradeName value between 0 and 255")
	String tradeName;
	@Positive(message = "Customer: wave cannot be negative")
	Integer wave;
	@Size(max = 3, message = "Customer: segment value between 0 and 3")
	String segment;
	@Size(max = 50, message = "Customer: workArea value between 0 and 50")
	String workArea;
	@Size(max = 50, message = "Customer: subWorkArea value between 0 and 50")
	String subWorkArea;
	@Positive(message = "Customer: minAmount cannot be negative")
	BigDecimal minAmount;
	@Size(max = 25, message = "Customer: channelOrigin value between 0 and 25")
	String channelOrigin;
	@Size(max = 255, message = "Customer: adviser value between 0 and 255")
	String adviser;
	String dexCode;
	@Size(max = 3, message = "Customer: currency value between 0 and 3")
	String currency;
	Integer state;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
	LocalDate registryDate;
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
	LocalDate dischargeDate;
	Boolean allowElectronicInvoice;
	Boolean perfectCustomer;
	@Size(max = 255, message = "Customer: image value between 0 and 255")
	String image;
	@Size(max = 6, message = "Customer: prefixProductCode value between 0 and 6")
	String prefixProductCode;
	Integer erpCode;
	Boolean newCustomer;
	Boolean updatedCustomer;

	@Valid
	List<BusinessDTO> business;
	List<PaymentTypeDTO> sellIns;
	List<PaymentTypeDTO> sellOuts;
	@Valid
	List<DocumentDTO> documents;
	@Valid
	List<UserDTO> users;
}
