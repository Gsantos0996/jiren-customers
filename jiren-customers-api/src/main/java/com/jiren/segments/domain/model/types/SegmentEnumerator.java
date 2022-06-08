package com.jiren.segments.domain.model.types;

import com.jiren.segments.domain.exception.enumerator.SegmentExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SegmentEnumerator {
	
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
                .orElseThrow(() -> new MPlusApiException(SegmentExceptionEnumerator.NOT_FOUND_SEGMENT, code));
    }
    
}