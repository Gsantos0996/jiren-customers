package com.jiren.paymenttype.handler.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jiren.customers.adapter.rest.dto.paymenttype.GetPaymentTypeResponseDTO;
import com.jiren.paymenttype.service.dto.GetPaymentTypeServiceDTO;

@Mapper(componentModel = "spring")
public interface PaymentTypeGetMapper {

    List<GetPaymentTypeResponseDTO> toGetPaymentTypeResponseDtos(List<GetPaymentTypeServiceDTO> listGetPaymentTypeServiceDTO);

}
