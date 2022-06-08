package com.jiren.customers.domain.model.types;

import java.util.stream.Stream;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SegmentEnumerator{
	
    A("A", "Sector A"),
    B("B", "Sector B"),
    C("C", "Sector C"),
    D("D", "Sector D");
	
    String code;
    String description;

    public static SegmentEnumerator of(String code){
        return Stream.of(SegmentEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_SEGMENT, code));
    }
    
}