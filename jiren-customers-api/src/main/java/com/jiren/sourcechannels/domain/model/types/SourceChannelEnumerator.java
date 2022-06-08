package com.jiren.sourcechannels.domain.model.types;

import com.jiren.sourcechannels.domain.exception.enumerator.SourceChannelExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum SourceChannelEnumerator {

    DEX_MIGRATION("DEX_MIGRATION", "Migración Dex"),
    DIRECT_MIGRATION("DIRECT_MIGRATION", "Migración Directa"),
    LEAD_DIGITAL("LEAD_DIGITAL", "Lead Digital"),
    OTHERS("OTHERS", "Otros");

    String code;
    String description;

    public static SourceChannelEnumerator of(String code){
        return Stream.of(SourceChannelEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(SourceChannelExceptionEnumerator.NOT_FOUND_SOURCE_CHANNEL, code));
    }

}
