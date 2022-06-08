package com.jiren.legalsections.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnumerator {
	
	TERMINOS_CONDICIONES(0, "Términos y Condiciones"),
	POLITICAS_PRIVACIDAD(1, "Políticas de Privacidad");

    Integer code;
    String name;

}
