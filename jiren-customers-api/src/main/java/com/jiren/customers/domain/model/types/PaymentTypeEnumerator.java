package com.jiren.customers.domain.model.types;

import com.jiren.shared.models.types.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTypeEnumerator implements Type<Integer> {
	
	SELL_OUT (0, "Sell Out"),
    SELL_IN (1, "Sell In");

    private final Integer code;
    private final String description;
    
}
