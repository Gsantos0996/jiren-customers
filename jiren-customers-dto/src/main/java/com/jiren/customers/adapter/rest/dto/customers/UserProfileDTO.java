package com.jiren.customers.adapter.rest.dto.customers;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserProfileDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class UserProfileResponseDTO {
        CustomerProfileResponseDTO customer;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CustomerProfileResponseDTO {
        String id;
        String name;
        String tradeName;
        BigDecimal minAmount;
        UserDTO user;
        String segment;
        String channelOrigin;
        String workArea;
        String subWorkArea;
        List<BusinessDTO> business;
        List<SellInDTO> sellIns;
        List<DocumentDTO> documents;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class UserDTO {
        String id;
        String typeDocument;
        String numberDocument;
        String name;
        String firstLastName;
        String secondLastName;
        String email;
        String phone;
        Boolean owner;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class BusinessDTO {
        String id;
        String address;
        String district;
        String province;
        String department;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SellInDTO {
        String id;
        String paymentType;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class DocumentDTO {
        String id;
        String type;
        String number;
    }


}
