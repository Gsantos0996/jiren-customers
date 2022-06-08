package com.jiren.paymenttype.infraestructure.dao.plsql;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jiren.paymenttype.domain.dao.PaymentTypeDAO;
import com.jiren.paymenttype.domain.model.PaymentType;
import com.jiren.paymenttype.infraestructure.dao.mapper.PaymentTypeMapper;
import com.jiren.paymenttype.infraestructure.dao.repository.PaymentTypesRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PSQLPaymentTypeDAO implements PaymentTypeDAO{
	
	private final PaymentTypesRepository paymentTypesRepository;
	private final PaymentTypeMapper paymentTypeServiceMapper;
	
	@Override
	public List<PaymentType> getSellIns() {
		return paymentTypesRepository.findBySellinAndActive(Boolean.TRUE, Boolean.TRUE)
				.stream().map(paymentTypeServiceMapper::toPaymentType)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<PaymentType> getSellOuts() {
		return paymentTypesRepository.findBySelloutAndActive(Boolean.TRUE, Boolean.TRUE)
				.stream().map(paymentTypeServiceMapper::toPaymentType)
				.collect(Collectors.toList());
	}

}
