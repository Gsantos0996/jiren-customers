package com.jiren.customers.service.dto;

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
public class CustomerSuccessServiceDTO {
	String documentType;
    String documentNumber;
    String guid;    
}
