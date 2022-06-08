package com.jiren.customers.domain.model.types;

import java.util.stream.Stream;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ZoneEnumerator {

	NORTH(0, "NORTE"),
    SOUTH(1, "SUR"),
    CENTER(2, "CENTRO"),
    EAST(3, "ESTE"),
    WEST(4, "OESTE");

    Integer code;
    String name;

    public static ZoneEnumerator of(Integer code){
        return Stream.of(ZoneEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_ZONE, code));
    }
    
    public static ZoneEnumerator byName(String name){
    	return Stream.of(ZoneEnumerator.values())
    			.filter(x -> x.getName().equals(name))
    			.findFirst()
    			.orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_ZONE, name));
    }
    
}
