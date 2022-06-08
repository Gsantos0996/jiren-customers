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
public class DocumentDTO {
	String id;
	@NotBlank(message = "Document: type cannot be blank")
	@NotNull(message = "Document: type is required")
	@Size(max = 15, message = "Customer: type value between 0 and 15")
	String type;
	@NotBlank(message = "Document: number cannot be blank")
	@NotNull(message = "Document: number is required")
	@Size(max = 16, message = "Document: number value between 0 and 16")
	String number;
	@NotNull(message = "Document: docFiscal is required")
	Boolean docFiscal;
}
