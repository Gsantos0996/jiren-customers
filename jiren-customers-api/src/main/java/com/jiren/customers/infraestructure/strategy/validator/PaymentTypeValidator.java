package com.jiren.customers.infraestructure.strategy.validator;

import java.util.ArrayList;
import java.util.List;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.customers.domain.model.types.PaymentTypeEnumerator;
import com.jiren.customers.infraestructure.dao.repository.PaymentTypeRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.utils.Helper;

public class PaymentTypeValidator {

	static final int SIZE_MAX_255 = 255;
	static final Boolean ENABLE_SELL = true;

	public static List<String> paymentType(String paymentType, String ownerDocumentNumber, 
			PaymentTypeEnumerator paymentEnum, List<String> listPaymentType, PaymentTypeRepository paymentTypeRepository) {
        List<String> errors = new ArrayList<>();
        try {
        	String field = "Medio de Pago";
            if (Helper.isNullOrEmpty(paymentType))
                throw new MPlusApiException(CustomerExceptionEnumerator.BAD_REQUEST_MANDATORY_FIELD, field);

            if (listPaymentType.contains(ownerDocumentNumber + paymentType))
    			throw new MPlusApiException(CustomerExceptionEnumerator.CONFLICT_DUPLICATED_PAYMENT_TYPE_MYPE, ownerDocumentNumber, paymentType);
            
            if (paymentTypeRepository.findByGuid(paymentType) == null) 
            	throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_PAYMENT_TYPE, paymentType);
            
            if (paymentEnum == PaymentTypeEnumerator.SELL_IN) {
	            if (paymentTypeRepository.findByGuidAndSellin(paymentType, ENABLE_SELL) == null) 
	            	throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_PAYMENT_TYPE, paymentType);
            }else {
            	if (paymentTypeRepository.findByGuidAndSellout(paymentType, ENABLE_SELL) == null) 
	            	throw new MPlusApiException(CustomerExceptionEnumerator.NOT_FOUND_PAYMENT_TYPE, paymentType);
            }
        }catch(Exception e){
            errors.add(e.getMessage());
        }
        return errors;
    }
    
}
