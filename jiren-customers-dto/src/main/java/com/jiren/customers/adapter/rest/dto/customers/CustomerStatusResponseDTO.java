package com.jiren.customers.adapter.rest.dto.customers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatusResponseDTO {
    private Integer code;
    private String description;
}
