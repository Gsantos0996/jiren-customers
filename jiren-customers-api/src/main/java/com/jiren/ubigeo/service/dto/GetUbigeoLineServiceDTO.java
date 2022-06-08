package com.jiren.ubigeo.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUbigeoLineServiceDTO {
    private String id;
    private String district;
    private String province;
    private String department;
}
