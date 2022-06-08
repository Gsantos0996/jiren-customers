package com.jiren.customers.handler;

import com.jiren.customers.adapter.rest.dto.customers.*;
import com.jiren.customers.domain.exception.enumerator.filter.CustomerFilter;
import com.jiren.customers.handler.mapper.CustomerSaveMapper;
import com.jiren.customers.service.CustomerExcelService;
import com.jiren.customers.service.CustomerService;
import com.jiren.customers.service.dto.CustomerServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaveCustomerHandler {

	private final CustomerService customerService;
	private final CustomerExcelService customerExcelService;
	
	private final CustomerSaveMapper customerMapper;
	
	public void createCustomer(CustomerRestDTO customerRestDTO) {
		CustomerServiceDTO customerServiceDTO = customerMapper.toCustomerServiceDTO(customerRestDTO);
		customerServiceDTO.setUserAudit(SecurityContextHolder.getContext().getAuthentication().getName());
		customerService.createCustomer( customerServiceDTO );
	}

	public void updateCustomer(CustomerRestDTO customerRestDTO) {
		CustomerServiceDTO customerServiceDTO = customerMapper.toCustomerServiceDTO(customerRestDTO);
		customerServiceDTO.setUserAudit(SecurityContextHolder.getContext().getAuthentication().getName());
		customerService.updateCustomer( customerServiceDTO );
	}	

	public CustomersExcelDTO createCustomerExcel(MultipartFile file, Boolean save) throws IOException, InterruptedException {
		String userAudit = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerMapper.toCustomersExcelDTO( customerExcelService.createCustomers(file, userAudit, save) );
	}

	public CustomerRestPageDTO getCustomers(CustomerFilter filter, PageRequest pageable) {
		return customerMapper.toCustomerRestPageDTO( customerService.getPageCustomers(filter, pageable) );
	}

	public GetCustomerRestDTO getCustomer(String guid) {
		return customerMapper.toCustomerRestDTO( customerService.getCustomer(guid) );
	}

	public GetCustomersRestDTO getCustomers(String guids) {
		return GetCustomersRestDTO.builder().customers(
				customerService.getCustomers(List.of(guids.split(",")))
					.stream()
					.map(customerMapper::toCustomerRestDTO)
					.collect(Collectors.toList()))
				.build();
	}

	public ByteArrayInputStream getTemplateExcel(String fileName) throws IOException {
		return customerExcelService.getTemplateExcel(fileName);
	}

	public List<CustomerStatusResponseDTO> listCustomerStatus() {
		return customerMapper.toCustomerStatusResponseDto(customerService.listCustomerStatus());
	}
}