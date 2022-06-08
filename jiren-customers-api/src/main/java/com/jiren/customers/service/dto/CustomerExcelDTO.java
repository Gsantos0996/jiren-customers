package com.jiren.customers.service.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiren.customers.domain.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerExcelDTO {

	List<Customer> customers;
	List<String> errors;
}
