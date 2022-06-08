package com.jiren.customers.adapter.rest.dto.customers;

import javax.validation.constraints.Email;
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
public class UserDTO {
	String id;
	
	@NotBlank(message = "User: typeDocument cannot be blank")
	@NotNull(message = "User: typeDocument is required")
	@Size(max = 15, message = "User: typeDocument value between 0 and 15")
	String typeDocument;
	@NotBlank(message = "User: numberDocument cannot be blank")
	@NotNull(message = "User: numberDocument is required")
	@Size(max = 16, message = "User: numberDocument value between 0 and 16")
	String numberDocument;
	@NotBlank(message = "User: name cannot be blank")
	@NotNull(message = "User: name is required")
	@Size(max = 255, message = "User: name value between 0 and 255")
	String name;
	@NotBlank(message = "User: firstLastName cannot be blank")
	@NotNull(message = "User: firstLastName is required")
	@Size(max = 255, message = "User: firstLastName value between 0 and 255")
	String firstLastName;
	@Size(max = 255, message = "User: secondLastName value between 0 and 255")
	String secondLastName;
	@Email(message = "User: invalid email")
	@Size(max = 100, message = "User: phone value between 0 and 100")
	String email;
	@NotBlank(message = "User: phone cannot be blank")
	@NotNull(message = "User: phone is required")
	@Size(max = 15, message = "User: phone value between 0 and 15")
	String phone;
	@NotNull(message = "User: owner is required")
	Boolean owner;
}
