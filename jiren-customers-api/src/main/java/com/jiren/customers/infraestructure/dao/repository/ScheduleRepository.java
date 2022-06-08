package com.jiren.customers.infraestructure.dao.repository;

import com.jiren.customers.infraestructure.dao.jpa.JPASchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<JPASchedule, String>{

	@Modifying(clearAutomatically = false)
	@Query(value =
		"UPDATE customers.schedule " +
			"	SET active = false " +
			" FROM customers.business " +
			"	WHERE schedule.business_guid = business.guid " +
			" AND business.customer_guid = :idCustomer", nativeQuery = true)
	void inactiveBycustomerGuid(@Param("idCustomer") String guid);
}
