package com.jiren.customers.adapter.rest.dto.paymenttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentTypeResponseDTO {

    String id;
    String description;
	String name;
	String alternativeName;
	String mode;
	Integer index;
	Boolean active;
}
