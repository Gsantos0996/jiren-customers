package com.jiren.customers.infraestructure.dao.repository;

import com.jiren.customers.infraestructure.dao.jpa.JPABusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BusinessRepository extends JpaRepository<JPABusiness, String>{

	@Modifying(clearAutomatically = false)
	@Query("update JPABusiness bus set bus.active = false where bus.customer.guid = :idCustomer")
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
	
	JPABusiness findByGuid(String guid);
	
	JPABusiness findByAddress(String address);
	
}
