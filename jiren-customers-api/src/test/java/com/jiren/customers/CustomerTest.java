package com.jiren.customers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jiren.customers.service.dto.GetCustomerServiceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jiren.customers.service.CustomerService;
import com.jiren.customers.service.dto.CustomerServiceDTO;

class CustomerTest {

	CustomerService customerService;
	
	@BeforeEach
	void setUp() {
		customerService = mock(CustomerService.class);
	}	
	
	@Test
	@DisplayName("Find by guid of Service")
	void findByGuidService() {
		String guid = "8cD2ITaDxI";
		String businessName = "Empresa Antonio Duran SA.";
		GetCustomerServiceDTO mockCustomerServiceDTO = new GetCustomerServiceDTO();
		mockCustomerServiceDTO.setId(guid);
		mockCustomerServiceDTO.setBusinessName(businessName);
		when(customerService.getCustomer(any())).thenReturn(mockCustomerServiceDTO);

		GetCustomerServiceDTO customerServiceDTO = customerService.getCustomer(guid);
		assertNotNull(customerServiceDTO);
		assertEquals(guid, customerServiceDTO.getId());
		assertEquals(businessName, customerServiceDTO.getBusinessName());
	}
	
}
