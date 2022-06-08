package com.jiren.customers.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jiren.customers.infraestructure.dao.jpa.JPACustomer;

import java.util.Collection;
import java.util.List;

public interface CustomerRepository extends JpaRepository<JPACustomer, String>, JpaSpecificationExecutor<JPACustomer>{

	JPACustomer findByGuid(String guid);
	
	JPACustomer findByBusinessName(String businessName);

	Collection<JPACustomer> findByGuidIn(List<String> guids);
	
}
