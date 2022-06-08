package com.jiren.documents.infraestructure.service;

import com.jiren.customers.domain.model.types.DocumentTypeEnumerator;
import com.jiren.documents.service.dto.GetDocumentServiceDTO;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentPredicates {

    public static final String NATURAL_DOCUMENT_TYPE = "natural";

    public static Predicate<DocumentTypeEnumerator> isDocumentForLogin(){
        return d -> d.getType().equalsIgnoreCase(NATURAL_DOCUMENT_TYPE) && d.isEnabled();
    }

    public static Predicate<DocumentTypeEnumerator> isDocumentForInvoice(){
        return d-> d.isDocFiscalEnabled() && d.isEnabled();
    }

    public static Predicate<DocumentTypeEnumerator> isDocumentEnabled(){
        return d-> d.isEnabled();
    }

    public static List<GetDocumentServiceDTO> filterDocuments (Predicate<DocumentTypeEnumerator> predicate) {
        return Stream.of(DocumentTypeEnumerator.values())
                .filter(predicate)
                .map(document -> new GetDocumentServiceDTO(document.getCode(), document.getType(), document.getDescription(), document.isDocFiscalEnabled()))
                .collect(Collectors.toList());
    }
}
