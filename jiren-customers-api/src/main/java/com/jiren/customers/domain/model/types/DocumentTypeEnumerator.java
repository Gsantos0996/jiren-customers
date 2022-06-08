package com.jiren.customers.domain.model.types;

import java.util.stream.Stream;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentTypeEnumerator {

    DNI("DNI", "DNI - Documento Nacional de Identidad","NATURAL", true,true),
    CE("CE", "Carnet de Extranjería","NATURAL", true,false),
    RUC("RUC", "RUC - Registro Unico de Contribuyentes","JURIDICO", true, true);

    String code;
    String description;
    String type;
    boolean enabled;
    boolean docFiscalEnabled;

    public static DocumentTypeEnumerator of(String code){
        return Stream.of(DocumentTypeEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_DOCUMENT_TYPE, code));
    }
    
}
