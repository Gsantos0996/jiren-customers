package com.jiren.customers.adapter.rest.dto.customers;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomersExcelDTO {

	private String message;
    private List<String> errors;
    private List<CustomerSuccessDTO> success;
}
