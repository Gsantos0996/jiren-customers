package com.jiren.legalsections.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.legalsections.infraestructure.dao.jpa.JpaUser;

public interface UsersRepository extends JpaRepository<JpaUser, String>{
	
	JpaUser findByGuid(String guid);
	
}
