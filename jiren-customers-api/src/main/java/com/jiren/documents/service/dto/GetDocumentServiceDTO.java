package com.jiren.documents.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDocumentServiceDTO {

    String id;
    String personType;
    String description;
    Boolean docFiscal;

}
