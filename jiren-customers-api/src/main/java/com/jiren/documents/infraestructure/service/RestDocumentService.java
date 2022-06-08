package com.jiren.documents.infraestructure.service;

import com.jiren.customers.domain.model.types.DocumentTypeEnumerator;
import com.jiren.customers.domain.model.types.DocumentUsesEnumerator;
import com.jiren.documents.domain.exception.enumerator.DocumentExceptionEnumerator;
import com.jiren.documents.service.DocumentService;
import com.jiren.documents.service.dto.GetDocumentServiceDTO;
import com.jiren.shared.exception.MPlusApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestDocumentService implements DocumentService {

    public static final String LOGIN_USE = "login";
    public static final String CUSTOMER_INVOICE_USE = "invoice";

    @Override
    public List<GetDocumentServiceDTO> listDocument(String forUse) {

        if(forUse.equalsIgnoreCase(LOGIN_USE)){
            return DocumentUsesEnumerator.FOR_LOGIN.getDocuments().stream()
                    .map(document ->  buildGetDocumentServiceDTO(document))
                    .collect(Collectors.toList());

        }else if(forUse.equalsIgnoreCase(CUSTOMER_INVOICE_USE)){
            return DocumentUsesEnumerator.FOR_INVOICE.getDocuments().stream()
                    .map(document ->  buildGetDocumentServiceDTO(document))
                    .collect(Collectors.toList());

        }else{
            throw new MPlusApiException(DocumentExceptionEnumerator.DOCUMENT_FILTER_INVALID);
        }
    }

    private GetDocumentServiceDTO buildGetDocumentServiceDTO(DocumentTypeEnumerator documentTypeEnumerator){
        return GetDocumentServiceDTO.builder()
                .id(documentTypeEnumerator.getCode())
                .personType(documentTypeEnumerator.getType())
                .description(documentTypeEnumerator.getDescription())
                .docFiscal(documentTypeEnumerator.isDocFiscalEnabled())
                .build();
    }

}
