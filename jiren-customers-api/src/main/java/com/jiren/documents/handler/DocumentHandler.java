package com.jiren.documents.handler;

import com.jiren.customers.adapter.rest.dto.documents.GetDocumentResponseDTO;
import com.jiren.documents.handler.mapper.DocumentMapper;
import com.jiren.documents.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentHandler {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    public List<GetDocumentResponseDTO> listDocument(String forUse) {
        return documentMapper.toGetDocumentResponseDtos(documentService.listDocument(forUse));
    }
}
