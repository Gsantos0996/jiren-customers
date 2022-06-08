package com.jiren.customers.adapter.rest.dto.customers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@Builder
public class GetCustomersRestDTO {
    private List<GetCustomerRestDTO> customers;
}
