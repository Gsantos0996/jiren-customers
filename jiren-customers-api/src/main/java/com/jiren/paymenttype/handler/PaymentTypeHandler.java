package com.jiren.paymenttype.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jiren.customers.adapter.rest.dto.paymenttype.GetPaymentTypeResponseDTO;
import com.jiren.paymenttype.handler.mapper.PaymentTypeGetMapper;
import com.jiren.paymenttype.service.PaymentTypeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentTypeHandler {

    private final PaymentTypeService paymentTypeService;
    private final PaymentTypeGetMapper paymentTypeMapper;

    public List<GetPaymentTypeResponseDTO> listSellIns() {
        return paymentTypeMapper.toGetPaymentTypeResponseDtos(paymentTypeService.listSellIns());
    }
    
    public List<GetPaymentTypeResponseDTO> listSellOuts() {
    	return paymentTypeMapper.toGetPaymentTypeResponseDtos(paymentTypeService.listSellOuts());
    }
}
