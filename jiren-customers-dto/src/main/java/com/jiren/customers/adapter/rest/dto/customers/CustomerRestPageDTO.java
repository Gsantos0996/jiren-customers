package com.jiren.customers.adapter.rest.dto.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRestPageDTO {
	List<GetCustomerRestDTO> customers;
	PageRenderDTO pageRender;
}
