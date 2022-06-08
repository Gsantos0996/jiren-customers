package com.jiren.customers.service;

import com.jiren.customers.service.dto.CustomerStatusDTO;
import com.jiren.customers.service.dto.GetCustomerServiceDTO;
import org.springframework.data.domain.PageRequest;

import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.service.dto.CustomerPageServiceDTO;
import com.jiren.customers.service.dto.CustomerServiceDTO;

import java.util.List;

public interface CustomerService {
	void createCustomer(CustomerServiceDTO customerServiceDTO);	
	void updateCustomer(CustomerServiceDTO customerServiceDTO);
	GetCustomerServiceDTO getCustomer(String guid);
	List<GetCustomerServiceDTO> getCustomers(List<String> guid);
	CustomerPageServiceDTO getPageCustomers(CustomerFilter filter, PageRequest pageable);
	List<CustomerStatusDTO> listCustomerStatus();

}
