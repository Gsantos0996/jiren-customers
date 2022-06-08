package com.jiren.shared.models.types;

import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.exception.WorAreaExceptionEnumerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

import static com.jiren.shared.models.types.SubWorkAreaEnumerator.*;

@Getter
@AllArgsConstructor
public enum WorkAreaEnumerator {

	GASTRONOMY("GASTRONOMY", "Gastronomía", CHICKEN_GRILL, CHIFA,
            SEA_FOOD,MENUS,SANDWICH_SHOP,INTERNATIONAL_FOOD, DARK_KITCHEN),
    BAKING("BAKING", "Panificación",SW_590,SW_580,SW_120,SW_PR0);

    String code;
    String description;
    List<SubWorkAreaEnumerator> subWorkAreaList;

    WorkAreaEnumerator(String code, String description, SubWorkAreaEnumerator... subWorkAreas){
        this.code = code;
        this.description = description;
        this.subWorkAreaList = List.of(subWorkAreas);
    }

    public static WorkAreaEnumerator of(String code){
        return Stream.of(WorkAreaEnumerator.values())
                .filter(x -> x.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(WorAreaExceptionEnumerator.NOT_FOUND_WORK_AREA, code));
    }

    public List<SubWorkAreaEnumerator> getSubWorkAreas(){
        return subWorkAreaList;
    }
    
}
