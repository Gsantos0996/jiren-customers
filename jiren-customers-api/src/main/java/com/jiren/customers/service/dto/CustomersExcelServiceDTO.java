package com.jiren.customers.service.dto;

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
public class CustomersExcelServiceDTO {

	private String message;
    private List<String> errors;
    private List<CustomerSuccessServiceDTO> success;
}
