package com.jiren.paymenttype.service;

import java.util.List;

import com.jiren.paymenttype.service.dto.GetPaymentTypeServiceDTO;

public interface PaymentTypeService {

    List<GetPaymentTypeServiceDTO> listSellIns();
    
    List<GetPaymentTypeServiceDTO> listSellOuts();
}
