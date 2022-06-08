package com.jiren.legalsections.infraestructure.dao.jpa;

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
@Table(schema = "customers", name = "user")
public class JpaUser {
	@Id
	String guid;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_guid", referencedColumnName = "guid", nullable = false)
	JpaCustomer customer;
	
	String typeDocument;
	String numberDocument;
	String name;
	String firstLastName;	
	String secondLastName;	
	String email;
	String phone;
	Boolean owner;
	Boolean active;
	
	String userCreated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateCreated;
	String userUpdated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
	LocalDateTime dateUpdated;
}
