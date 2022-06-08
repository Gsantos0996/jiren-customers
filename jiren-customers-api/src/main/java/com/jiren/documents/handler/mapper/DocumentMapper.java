package com.jiren.documents.handler.mapper;

import com.jiren.customers.adapter.rest.dto.documents.GetDocumentResponseDTO;
import com.jiren.documents.service.dto.GetDocumentServiceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    List<GetDocumentResponseDTO> toGetDocumentResponseDtos(List<GetDocumentServiceDTO> listGetDocumentoServiceDTO);

}
