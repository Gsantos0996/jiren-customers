package com.jiren.customers.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.customers.infraestructure.dao.jpa.JPAPaymentType;

public interface PaymentTypeRepository extends JpaRepository<JPAPaymentType, String>{

	JPAPaymentType findByGuid(String guid);
	
	JPAPaymentType findByGuidAndSellin(String guid, Boolean sellIn);
	
	JPAPaymentType findByGuidAndSellout(String guid, Boolean sellOut);
	
}
