package com.jiren.paymenttype.infraestructure.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.paymenttype.infraestructure.dao.jpa.JpaPaymentType;

public interface PaymentTypesRepository extends JpaRepository<JpaPaymentType, String>{

	List<JpaPaymentType> findBySellinAndActive(Boolean sellIn, Boolean active);
	
	List<JpaPaymentType> findBySelloutAndActive(Boolean sellOut, Boolean active);
	
}
