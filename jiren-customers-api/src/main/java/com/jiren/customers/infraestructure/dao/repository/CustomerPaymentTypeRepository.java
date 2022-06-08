package com.jiren.customers.infraestructure.dao.repository;

import com.jiren.customers.infraestructure.dao.jpa.JPACustomerPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerPaymentTypeRepository extends JpaRepository<JPACustomerPaymentType, String>{

	@Modifying(clearAutomatically = false)
	@Query("update JPACustomerPaymentType cpt set cpt.active = false where cpt.customer.guid = :idCustomer")
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
	
	JPACustomerPaymentType findByCustomerGuidAndPaymentTypeGuidAndType(String customerGuid, String paymentTypeGuid, Integer type);
	
}
