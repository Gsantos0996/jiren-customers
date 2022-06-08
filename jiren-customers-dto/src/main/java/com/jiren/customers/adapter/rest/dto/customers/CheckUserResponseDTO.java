package com.jiren.customers.adapter.rest.dto.customers;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckUserResponseDTO {
    String typeDocument;
    String numberDocument;
}
