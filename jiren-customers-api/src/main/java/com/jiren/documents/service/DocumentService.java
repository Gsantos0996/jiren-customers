package com.jiren.documents.service;

import com.jiren.documents.service.dto.GetDocumentServiceDTO;

import java.util.List;

public interface DocumentService {

    List<GetDocumentServiceDTO> listDocument(String forUse);
}
