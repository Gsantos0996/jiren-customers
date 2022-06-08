package com.jiren.customers.infraestructure.dao.repository;

import com.jiren.customers.infraestructure.dao.jpa.JPADocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<JPADocument, String>{

	@Modifying(clearAutomatically = false)
	@Query("update JPADocument doc set doc.active = false where doc.customer.guid = :idCustomer")
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
	
	JPADocument findByTypeAndNumber(String type, String number);
	
}
