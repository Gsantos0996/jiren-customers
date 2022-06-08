package com.jiren.customers.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.domain.model.Customer;

import java.util.List;

public interface CustomerDAO {	
	Customer getCustomer(String guid);
	Customer getProfileCustomer(String guid);
	Page<Customer> getPageCustomers(CustomerFilter filter, Pageable pageable);
	String create(Customer customer);
	void update(Customer customer);
	List<Customer> getCustomers(List<String> guids);
}
