package com.jiren.customers.adapter.rest.dto.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDocumentResponseDTO {

    String id;
    String personType;
    String description;
    Boolean docFiscal;

}
