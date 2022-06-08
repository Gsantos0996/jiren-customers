package com.jiren.legalsections.infraestructure.dao.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "customers", name = "customer")
public class JpaCustomer {
	@Id
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateCreated;
	String userUpdated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateUpdated;

}
