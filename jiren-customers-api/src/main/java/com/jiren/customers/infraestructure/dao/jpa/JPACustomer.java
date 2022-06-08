package com.jiren.customers.infraestructure.dao.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(schema = "customers", name = "customer")
public class JPACustomer {
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	@Where(clause = "active = 'true'")
	Set<JPABusiness> customerBusiness = new HashSet<JPABusiness>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	@Where(clause = "active = 'true'")
	Set<JPACustomerPaymentType> customerSells = new HashSet<JPACustomerPaymentType>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	@Where(clause = "active = 'true'")
	Set<JPADocument> customerDocuments = new HashSet<JPADocument>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
	@Where(clause = "active = 'true'")
	Set<JPAUser> customerUsers = new HashSet<JPAUser>();
}
