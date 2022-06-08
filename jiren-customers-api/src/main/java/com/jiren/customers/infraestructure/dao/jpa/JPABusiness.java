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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "customers", name = "business")
public class JPABusiness {
	@Id
	String guid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_guid", referencedColumnName = "guid", nullable = false)
	JPACustomer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ubigeo_code", referencedColumnName = "guid", nullable = false)
	JPAUbigeo ubigeo;

	Integer deliveryDistance;
	BigDecimal deliveryCost;	
	String address;	
	String postalCode;	
	String reference;	
	BigDecimal latitude;	
	BigDecimal longitude;	
	Integer zone;	
	String annex;
	Boolean active;
	
	String userCreated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateCreated;
	String userUpdated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateUpdated;
}
