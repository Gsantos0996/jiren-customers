package com.jiren.paymenttype.infraestructure.interfaces.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jiren.paymenttype.domain.dao.PaymentTypeDAO;
import com.jiren.paymenttype.domain.model.PaymentType;
import com.jiren.paymenttype.service.PaymentTypeService;
import com.jiren.paymenttype.service.dto.GetPaymentTypeServiceDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestPaymentTypeService implements PaymentTypeService {
	
	private final PaymentTypeDAO paymentTypeDAO;
	
	@Override
	public List<GetPaymentTypeServiceDTO> listSellIns() {
		return paymentTypeDAO.getSellIns().stream()
				.map(x -> new GetPaymentTypeServiceDTO(
						x.getGuid(), x.getDescription(), 
						x.getName(), x.getAlternativeName(), 
						x.getMode(), x.getIndex(), x.getActive()))
						.collect(Collectors.toList());
	}

	@Override
	public List<GetPaymentTypeServiceDTO> listSellOuts() {
		return paymentTypeDAO.getSellOuts().stream()
				.sorted(Comparator.comparing(PaymentType::getIndex))
				.map(x -> new GetPaymentTypeServiceDTO(
						x.getGuid(), x.getDescription(), 
						x.getName(), x.getAlternativeName(), 
						x.getMode(), x.getIndex(), x.getActive()))
				.collect(Collectors.toList());
	}
	

}
