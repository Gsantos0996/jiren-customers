package com.jiren.paymenttype.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentTypeServiceDTO {

    String id;
    String description;
	String name;
	String alternativeName;
	String mode;
	Integer index;
	Boolean active;
}
