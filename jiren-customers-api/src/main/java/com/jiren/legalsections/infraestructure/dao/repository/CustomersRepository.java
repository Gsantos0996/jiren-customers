package com.jiren.legalsections.infraestructure.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.legalsections.infraestructure.dao.jpa.JpaCustomer;

public interface CustomersRepository extends JpaRepository<JpaCustomer, String> {

}
