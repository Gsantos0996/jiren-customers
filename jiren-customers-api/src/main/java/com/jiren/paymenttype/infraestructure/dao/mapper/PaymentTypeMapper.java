package com.jiren.paymenttype.infraestructure.dao.mapper;

import org.mapstruct.Mapper;

import com.jiren.paymenttype.domain.model.PaymentType;
import com.jiren.paymenttype.infraestructure.dao.jpa.JpaPaymentType;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {

	JpaPaymentType toJpaPaymentType(PaymentType paymentType);
	
	PaymentType toPaymentType(JpaPaymentType paymentType);
}
