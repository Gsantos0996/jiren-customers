package com.jiren.paymenttype.domain.dao;

import java.util.List;

import com.jiren.paymenttype.domain.model.PaymentType;

public interface PaymentTypeDAO {

	List<PaymentType> getSellIns();
	List<PaymentType> getSellOuts();
}
