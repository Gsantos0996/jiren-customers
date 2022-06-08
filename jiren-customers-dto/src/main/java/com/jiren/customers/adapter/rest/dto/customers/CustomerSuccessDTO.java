package com.jiren.customers.adapter.rest.dto.customers;

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
public class CustomerSuccessDTO {

	private String documentType;
    private String documentNumber;
    private String guid;
    
}
