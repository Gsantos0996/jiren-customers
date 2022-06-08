package com.jiren.customers.adapter.rest.dto.customers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentTypeDTO {
	String id;
	@NotBlank(message = "PaymentType: paymentType cannot be blank")
	@NotNull(message = "PaymentType: paymentType is required")
	@Size(max = 15, message = "PaymentType: paymentType value between 0 and 15")
	String paymentType;
}
