package com.jiren.ubigeo.domain.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ZoneEnumerator {

	NORTH(1, "NORTE"),
    SOUTH(2, "SUR"),
    CENTER(3, "CENTRO"),
    EAST(4, "ESTE"),
    WEST(5, "OESTE");

    Integer code;
    String name;

}
