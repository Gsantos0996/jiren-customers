package com.jiren.customers.domain.model.types;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

import static com.jiren.customers.domain.model.types.DocumentTypeEnumerator.*;

@Getter
@AllArgsConstructor
public enum DocumentUsesEnumerator {

    FOR_LOGIN("FOR_LOGIN", "Documents for login",DNI,CE,RUC),
    FOR_INVOICE("FOR_INVOICE", "Documents for invoice",DNI,RUC);

    String code;
    String description;
    List<DocumentTypeEnumerator> documentTypeEnumerators;

    DocumentUsesEnumerator(String code, String description, DocumentTypeEnumerator... documentTypeEnumerators){
        this.code = code;
        this.description = description;
        this.documentTypeEnumerators = List.of(documentTypeEnumerators);
    }

    public static DocumentUsesEnumerator of(String code){
        return Stream.of(DocumentUsesEnumerator.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_DOCUMENT_TYPE, code));
    }

    public List<DocumentTypeEnumerator> getDocuments(){
        return documentTypeEnumerators;
    }
}
