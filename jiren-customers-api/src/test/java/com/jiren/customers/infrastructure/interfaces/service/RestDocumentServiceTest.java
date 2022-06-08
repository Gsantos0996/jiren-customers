package com.jiren.customers.infrastructure.interfaces.service;

import com.jiren.customers.UnitTestBase;
import com.jiren.documents.infraestructure.service.RestDocumentService;
import com.jiren.documents.service.DocumentService;
import com.jiren.documents.service.dto.GetDocumentServiceDTO;
import com.jiren.shared.exception.MPlusApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RestDocumentServiceTest extends UnitTestBase {

    public static final long MAX_DOCUMENTS_FOR_LOGIN = 2L;
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        this.documentService = new RestDocumentService();
    }

    @Test
    void givenValidLoginParamWhenListDocumentThenSuccess() {

        List<GetDocumentServiceDTO> documentServiceDTOList = documentService.listDocument("login");

        long countDocuments = documentServiceDTOList.stream()
                .filter(d->d.getId().equalsIgnoreCase("DNI") || d.getId().equalsIgnoreCase("CE"))
                .count();

        Assertions.assertEquals(MAX_DOCUMENTS_FOR_LOGIN,countDocuments);
    }

    @Test
    void givenValidInvoiceParamWhenListDocumentThenSuccess() {

        List<GetDocumentServiceDTO> documentServiceDTOList = documentService.listDocument("invoice");

        long countDocuments = documentServiceDTOList.stream()
                .filter(d->d.getId().equalsIgnoreCase("DNI") || d.getId().equalsIgnoreCase("RUC"))
                .count();

        Assertions.assertEquals(MAX_DOCUMENTS_FOR_LOGIN,countDocuments);
    }

    @Test
    void givenInValidParamWhenListDocumentThenError() {

        Assertions.assertThrows(MPlusApiException.class,()-> documentService.listDocument("anything"));

    }

}
