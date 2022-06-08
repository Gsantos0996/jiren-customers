package com.jiren.customers.domain.model.types;

import java.util.Optional;
import java.util.stream.Stream;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyEnumerator {

	PEN("PEN", "Sol", "S/"),
    USD("USD", "US Dollar", "US$");

    String code;
    String description;
    String symbol;

    public static CurrencyEnumerator of(String code){
        return Stream.of(CurrencyEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_CURRENCY, code));
    }

    public static Optional<CurrencyEnumerator> getIfPresent(String code){
        return Stream.of(CurrencyEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst();
    }
    
}
