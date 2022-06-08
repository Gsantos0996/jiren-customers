package com.jiren.customers.infraestructure.dao.repository;

import com.jiren.customers.infraestructure.dao.jpa.JPATimeWindow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeWindowRepository extends JpaRepository<JPATimeWindow, String>{
	
	@Modifying(clearAutomatically = false)
	@Query(value =
		"UPDATE customers.time_window " +
			"	SET active = false " +
			" FROM customers.business " +
			"	WHERE time_window.business_guid = business.guid " +
			" AND business.customer_guid = :idCustomer", nativeQuery = true)
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
}
