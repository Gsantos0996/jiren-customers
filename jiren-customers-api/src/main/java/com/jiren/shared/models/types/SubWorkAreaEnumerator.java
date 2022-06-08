package com.jiren.shared.models.types;

import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.exception.SubWorAreaExceptionEnumerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SubWorkAreaEnumerator {

    DARK_KITCHEN("DARK_KITCHEN", "Dark Kitchen","F70"),
    INTERNATIONAL_FOOD("INTERNATIONAL_FOOD", "Comida Internacional","R40"),
    SANDWICH_SHOP("SANDWICH_SHOP","Sanguchería","R50"),
    MENUS("MENUS","Menús","R40"),
    SEA_FOOD("SEA_FOOD","Cevichería","R30"),
    CHIFA("CHIFA","Chifa","R20"),
    CHICKEN_GRILL("CHICKEN_GRILL","Pollería","R10"),
    SW_590("SW_590","590","590"),
    SW_580("SW_580","580","580"),
    SW_120("SW_120","120","120"),
    SW_PR0("SW_PR0","PR0","PR0");

    String code;
    String description;
    String externalCode;

    public static SubWorkAreaEnumerator of(String code){
        return Stream.of(SubWorkAreaEnumerator.values())
                .filter(x -> x.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(SubWorAreaExceptionEnumerator.NOT_FOUND_SUBWORK_AREA, code));
    }

}
