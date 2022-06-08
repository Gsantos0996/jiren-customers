package com.jiren.customers.adapter.rest.dto.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CheckUserRestDTO {

	@NotNull(message = "Usuario: Nombre de usuario es requerido")
	String username;

}
