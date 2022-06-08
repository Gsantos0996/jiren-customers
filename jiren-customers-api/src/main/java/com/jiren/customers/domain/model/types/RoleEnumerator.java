package com.jiren.customers.domain.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnumerator {
	
	OWNER(0, "PROPIETARIO"),
    USER(1, "USUARIO");

    Integer code;
    String name;

}
