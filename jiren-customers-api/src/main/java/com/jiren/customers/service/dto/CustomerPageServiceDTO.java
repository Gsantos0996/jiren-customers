package com.jiren.customers.service.dto;

import com.jiren.customers.adapter.rest.dto.customers.PageRenderDTO;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPageServiceDTO {
	List<GetCustomerServiceDTO> customers;
	PageRenderDTO pageRender;
}
