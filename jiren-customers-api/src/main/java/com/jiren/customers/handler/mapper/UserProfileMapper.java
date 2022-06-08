package com.jiren.customers.handler.mapper;

import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.DocumentDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.SellInDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.UserDTO;
import com.jiren.customers.adapter.rest.dto.customers.UserProfileDTO.CustomerProfileResponseDTO;
import com.jiren.customers.domain.model.Customer;
import com.jiren.customers.domain.model.CustomerPaymentType;
import com.jiren.customers.domain.model.Document;
import com.jiren.customers.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mappings({
            @Mapping(source = "customer.guid", target = "id"),
            @Mapping(source = "customer.businessName", target = "name"),
            @Mapping(source = "customer.tradeName", target = "tradeName"),
            @Mapping(source = "customer.minAmount", target = "minAmount"),
            @Mapping(source = "customer.segment", target = "segment"),
            @Mapping(source = "customer.channelOrigin", target = "channelOrigin"),
            @Mapping(source = "customer.workArea", target = "workArea"),
            @Mapping(source = "customer.subWorkArea", target = "subWorkArea"),
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "customer.sellIns", target = "sellIns"),
            @Mapping(source = "customer.documents", target = "documents"),

    })
    CustomerProfileResponseDTO toCustomerProfileResponseDto(User user, Customer customer);

    @Mappings({
            @Mapping(source = "customerPaymentType.paymentType.guid", target = "id"),
            @Mapping(source = "customerPaymentType.paymentType.name", target = "paymentType")
    })
    SellInDTO toSellInDto(CustomerPaymentType customerPaymentType);

    @Mappings({
            @Mapping(source = "document.guid", target = "id"),
            @Mapping(source = "document.type", target = "type"),
            @Mapping(source = "document.number", target = "number"),
    })
    DocumentDTO toDocumentDto(Document document);

    @Mappings({
            @Mapping(source = "user.guid", target = "id"),
    })
    UserDTO toUserDto(User user);

}
