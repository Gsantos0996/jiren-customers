package com.jiren.customers.domain.model.types;

import java.util.stream.Stream;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnumerator {
	
	ACTIVE(0, "ACTIVO"),
    INACTIVE(1, "DE BAJA"),
    SUSPENDED(2, "SUSPENDIDO");

    Integer code;
    String name;

    public static StatusEnumerator of(Integer code){
        return Stream.of(StatusEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_STATUS, String.valueOf(code)));
    }
    
    public static StatusEnumerator byName(String name){
    	return Stream.of(StatusEnumerator.values())
    			.filter(x -> x.getName().equals(name))
    			.findFirst()
    			.orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_STATUS, name));
    }
}
