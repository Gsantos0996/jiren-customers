package com.jiren.customers.handler.mapper;

import com.jiren.customers.adapter.rest.dto.customers.*;
import com.jiren.customers.service.dto.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerSaveMapper {
	CustomerServiceDTO toCustomerServiceDTO(CustomerRestDTO customerRestDTO);
	GetCustomerRestDTO toCustomerRestDTO(GetCustomerServiceDTO customerServiceDTO);
	CustomersExcelDTO toCustomersExcelDTO(CustomersExcelServiceDTO customersExcelServiceDTO);
	CustomerRestPageDTO toCustomerRestPageDTO(CustomerPageServiceDTO customerServicePageDTO);
	List<CustomerStatusResponseDTO> toCustomerStatusResponseDto(List<CustomerStatusDTO> listCustomerStatusDTO);
}
