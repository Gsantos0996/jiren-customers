package com.jiren.customers.infraestructure.dao.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "customers", name = "customer_credit")
public class JPACustomerCredit {
	@Id
	String guid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_guid", referencedColumnName = "guid", nullable = false)
	JPACustomer customer;

	Integer erpCode;
	BigDecimal totalLine;	
	BigDecimal availableLine;	
	BigDecimal reservedLine;	
	LocalDateTime expirationDate;
	Integer deadlineDays;
	Boolean active;
	
	String userCreated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateCreated;
	String userUpdated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateUpdated;
}
