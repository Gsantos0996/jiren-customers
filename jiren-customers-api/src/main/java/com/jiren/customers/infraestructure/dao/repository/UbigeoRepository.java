package com.jiren.customers.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.customers.infraestructure.dao.jpa.JPAUbigeo;

public interface UbigeoRepository extends JpaRepository<JPAUbigeo, String>{

	JPAUbigeo findByGuid(String guid);
	
}
